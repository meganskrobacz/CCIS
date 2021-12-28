
import pygame as pg
import random
import copy

from pygame.display import update

class AIPaddle:
    def __init__(self, screen_rect, ball_rect, difficulty, markov_states):
        self.difficulty = difficulty
        self.screen_rect = screen_rect
        self.ball_Rect = ball_rect
        self.move_up = False
        self.move_up_markov = False
        self.move_down = False
        self.move_down_markov = False
        
        # If difficulty is hard, the paddle adjusts position 100% of the time
        if self.difficulty == 'hard':
            precision = 1
        # If medium, paddle only adjusts when the ball is on the AI side of the board
        elif self.difficulty == 'medium':
            precision = 2
        # If easy, only responds when the ball is in the first half of the board
        elif self.difficulty == 'easy':
            precision = 2
        
        width = self.screen_rect.width / precision
        surf = pg.Surface([width, self.screen_rect.height])
        self.screen_response_area_rect = surf.get_rect()
        self.markov_states = markov_states
        self.index_of_chosen_markov_state = -1

    def update(self, ball_rect, ball, paddle_rect):
        if self.difficulty == 'easy':
            return self.update_easy(ball_rect, ball, paddle_rect)
        elif self.difficulty == 'medium':
            return self.update_medium(ball_rect, ball, paddle_rect)
        else:
            return self.update_hard(ball_rect, ball, paddle_rect)
    
    '''
    On easy setting, the paddle will use a MDP to try and predict which quadrant
    the ball will land in, and move the paddle to the center of that quadrant.
    The paddle will also only respond when the ball is quite close
    to the paddle.
    '''
    def update_easy(self, ball_rect, ball, paddle_rect):
        if self.screen_response_area_rect.colliderect(ball_rect):
            self.bellman_equation(ball, ball_rect)
            markov_rect = self.markov_states[self.index_of_chosen_markov_state][1]
            if markov_rect.midleft[1] < paddle_rect.centery:
                self.move_up_markov = True
            else:
                self.move_down_markov = True
    
    '''
    On medium setting, the paddle will move ideally 1/2  of the time, and the
    remaining 1/2 of the time, it will randomly go up or down with equal 
    probability. The paddle will also only respond when the ball is on its side
    of the court.
    '''
    def update_medium(self, ball_rect, ball, paddle_rect):
        rand = random.random()
        if self.screen_response_area_rect.colliderect(ball_rect):
            if rand >= 0.50 and not ball.moving_away_from_AI:
                if rand >= 0.75:
                    self.move_up = True
                else:
                    self.move_down = True
            else:
                if ball_rect.centery < paddle_rect.centery:
                    if not ball.moving_away_from_AI:
                        self.move_up = True
                elif ball_rect.centery > paddle_rect.centery:
                    if not ball.moving_away_from_AI:
                        self.move_down = True
    
    '''
    On hard setting, the paddle will move ideally all of the time. The paddle 
    will also constantly update its position as soon as the ball is coming
    towards it.
    '''
    def update_hard(self, ball_rect, ball, paddle_rect):
        if self.screen_response_area_rect.colliderect(ball_rect):
            if ball_rect.centery < paddle_rect.centery:
                if not ball.moving_away_from_AI:
                    self.move_up = True
            elif ball_rect.centery > paddle_rect.centery:
                if not ball.moving_away_from_AI:
                    self.move_down = True
    
    def reset(self):
        '''reset upon each iteration of update'''
        self.move_up = False
        self.move_up_markov = False
        self.move_down = False
        self.move_down_markov = False
        for i in range(len(self.markov_states)):
            self.markov_states[i][0] = 0
        self.index_of_chosen_markov_state = -1

    def is_moving_down(self, ball):
        velocity_y_old = abs(ball.vel[1])
        velocity_y_new = abs(ball.true_pos[1] + ball.vel[1] * ball.speed)
        return velocity_y_new <= velocity_y_old

    def T(self, ball, index_of_state):
        midpoint = len(self.markov_states)
        if self.is_moving_down(ball):
            if index_of_state >= midpoint:
                return 0.66
            else:
                return 0.33
        else:
            if index_of_state < midpoint:
                return 0.66
            else:
                return 0.33

    def R(self, ball_rect, state):
        distance = abs(state.centery - ball_rect.centery)
        if distance <= 100:
            return 0.75
        elif distance <= 250:
            return 0.25
        elif distance >=500:
            return -0.75
        else:
            return 0


    def bellman_equation(self, ball, ball_rect):
        Gamma = 0.9
        bestValue=-1
        qValue = 0
        for i in range(len(self.markov_states)):
            qValue += self.T(ball, i)*(self.R(ball_rect, self.markov_states[i][1])) + Gamma*self.markov_states[i][0]
            if qValue > bestValue:
                bestValue = qValue
                self.index_of_chosen_markov_state = i
            self.markov_states[i][0]=bestValue

            