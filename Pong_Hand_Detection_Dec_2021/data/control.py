
import os
import pygame as pg

from data.handTracking import handDetector
from .states import classic, menu, mode, options, controls, audio, ghost, splash, keybinding, getkey
import cv2
import math

class Control():
    def __init__(self, fullscreen, difficulty, size):
        pg.mixer.pre_init(44100, -16, 1, 512)
        pg.init()
        pg.display.set_caption("Pong")
        self.screensize = (int(size[0]), int(size[1]))
        if fullscreen:
            self.screen = pg.display.set_mode(self.screensize, pg.FULLSCREEN)
        else:
            os.environ["SDL_VIDEO_CENTERED"] = "True"
            self.screen = pg.display.set_mode(self.screensize)
        self.screen_rect = self.screen.get_rect()
        self.clock = pg.time.Clock()
        self.fps = 60
        self.keys = pg.key.get_pressed()

        if difficulty == 'hard':
            markov_zones = 20
        elif difficulty == 'medium':
            markov_zones = 10
        elif difficulty == 'easy':
            markov_zones = 3

        self.markov_states = self.create_markov_states(markov_zones, self.screen_rect.width / 2)
        self.done = False
        self.state_dict = {
            "MENU"     : menu.Menu(self.screen_rect),
            "CLASSIC"  : classic.Classic(self.screen_rect, difficulty, self.markov_states),
            "CONTROLS" : controls.Controls(self.screen_rect),
            "MODE"     : mode.Mode(self.screen_rect),
            "OPTIONS"  : options.Options(self.screen_rect),
            "AUDIO"    : audio.Audio(self.screen_rect),
            "BALLS"    : ghost.Ghost(self.screen_rect, difficulty, self.markov_states),
            "SPLASH"   : splash.Splash(self.screen_rect),
            "KEYBINDING" : keybinding.KeyBinding(self.screen_rect),
            "GETKEY"   : getkey.GetKey(self.screen_rect)
        }
        self.state_name = "SPLASH"
        self.state = self.state_dict[self.state_name]
        
        # Hyperparameters for hand detetection input
        self.change_threshold = 5 # Minimum distance needed between two events to count as a change
        self.when_to_check = 500 # How many milliseconds to wait before re-running this event

        # Variables to update with each hand detection input
        self.previous_hand_distance = -1
        self.direction = 0

        # Creating the hand detection event for pygame
        self.hand_detection_event = pg.USEREVENT + 0
        pg.time.set_timer(self.hand_detection_event, self.when_to_check) 

        # Starting the hand detector code
        self.cap = cv2.VideoCapture(0)
        self.cap.set(3, 350)
        self.cap.set(4, 350)
        self.detector = handDetector()
        
    # Determines the distance between the index and thumb fingers
    def get_finger_distance(self):
        success, img = self.cap.read()
        hands = self.detector.findHands(img)
        lmList = self.detector.findPosition(hands)
        cv2.imshow("Image", img)
        cv2.waitKey(1)
        if len(lmList) != 0:
            x1, y1 = lmList[4][1], lmList[4][2]
            x2, y2 = lmList[8][1], lmList[8][2]
            return(math.hypot(x2 - x1, y2 - y1))
    
    # If -1, move up
    # If 0, stay the same
    # If 1, move down
    # Move up if moving fingers farther apart
    # Move down if moving fingers closer together
    # Stay the same if nothing
    def movement_changed(self, new_distance):
        difference = new_distance - self.previous_hand_distance
        if difference > self.change_threshold:
            return -1
        elif difference < (-1 * self.change_threshold):
            return 1
        else:
            return 0

    def event_loop(self):
        for event in pg.event.get():
            if event.type == pg.QUIT:
                self.quit = True
            elif event.type in (pg.KEYDOWN,pg.KEYUP):
                self.keys = pg.key.get_pressed()
            elif event.type == self.hand_detection_event:
                distance = self.get_finger_distance()
                if distance is not None:
                    self.direction = self.movement_changed(distance)
                    self.previous_hand_distance = distance
            self.state.get_event(event, self.keys)

    def change_state(self):
        if self.state.done:
            self.state.cleanup()
            self.state_name = self.state.next
            self.state.done = False
            self.state = self.state_dict[self.state_name]
            self.state.entry()
            
    def run(self):
        while not self.done:
            if self.state.quit:
                self.done = True
            now = pg.time.get_ticks()
            self.event_loop()
            self.change_state()
            self.state.update(now, self.keys, self.direction)
            self.state.render(self.screen)
            pg.display.update()
            self.clock.tick(self.fps)

    def create_markov_states(self, markov_zones, width):
        markov_states = []
        height_each_state = self.screen_rect.height / markov_zones
        top = 0
        while (top < self.screensize[1]):
            state = pg.Rect((0, top), (width, height_each_state))
            markov_states.append([0,state])
            top += height_each_state
        return markov_states