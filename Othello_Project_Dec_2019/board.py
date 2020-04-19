
import turtle
import time
from records import Records

SQUARE = 100


class Board:
    # PURPOSE
    # Constructor for the Board class. size should be an even integer.
    # SIGNATURE
    # __init__ :: Board, Integer => Board
    def __init__(self, size):
        self.size = size
        self.high_bound = SQUARE * size / 2
        self.low_bound = 0 - self.high_bound
        self.color = "white"
        self.winner_declared = False

    # PURPOSE
    # Draws the board.
    # SIGNATURE
    # draw_board :: Board => None
    def draw_board(self):
        NUM_SIDES = 4
        RIGHT_ANGLE = 90
        turtle.setup(self.size * SQUARE + SQUARE, self.size * SQUARE + SQUARE)
        turtle.screensize(self.size * SQUARE, self.size * SQUARE)
        turtle.bgcolor("white")
        turtle.title("Welcome to Othello!")

        # Create the turtle to draw the board
        self.othello = turtle.Turtle()
        self.othello.penup()
        self.othello.speed(0)
        self.othello.hideturtle()

        # Line color is black, fill color is green
        self.othello.color("black", "forest green")

        # Move the turtle to the upper left corner
        corner = -self.size * SQUARE / 2
        self.othello.setposition(corner, corner)

        # Draw the green background
        self.othello.begin_fill()
        for i in range(NUM_SIDES):
            self.othello.pendown()
            self.othello.forward(SQUARE * self.size)
            self.othello.left(RIGHT_ANGLE)
        self.othello.end_fill()

        # Draw the horizontal lines
        for i in range(self.size + 1):
            self.othello.setposition(corner, SQUARE * i + corner)
            self.draw_lines(self.othello, self.size)

        # Draw the vertical lines
        self.othello.left(RIGHT_ANGLE)
        for i in range(self.size + 1):
            self.othello.setposition(SQUARE * i + corner, corner)
            self.draw_lines(self.othello, self.size)

        # Draw the 4 start pieces
        self.start_pieces()

        # Get the reference to the screen
        screen = turtle.Screen()
        # Listen for click events
        screen.onclick(self.board_click)
        # Stops the window from closing
        turtle.done()

    # PURPOSE
    # Helper function to draw the lines on the board
    # SIGNATURE
    # draw_lines :: Board, Turtle, Integer => None
    def draw_lines(self, turt, n):
        turt.pendown()
        turt.forward(SQUARE * n)
        turt.penup()

    # PURPOSE
    # Figures out the correct coordinates to draw the piece for the
    # draw_piece function in Board.
    # SIGNATURE
    # correct_coordinate :: Board, Float => Integer
    def correct_coordinate(self, val):
        if val > self.low_bound and val < self.low_bound + SQUARE:
            return 0
        elif val > (self.low_bound + SQUARE) and val < (self.low_bound // 2):
            return 1
        elif val > (self.low_bound // 2) and val < (0 - SQUARE):
            return 2
        elif val > (0 - SQUARE) and val < 0:
            return 3
        elif val > 0 and val < SQUARE:
            return 4
        elif val > SQUARE and val < (self.high_bound // 2):
            return 5
        elif val > (self.high_bound // 2) and val < (self.high_bound - SQUARE):
            return 6
        elif val > (self.high_bound - SQUARE) and val < self.high_bound:
            return 7
        else:
            return -1

    # PURPOSE
    # Helper function which switches the colors of the tiles to be drawn.
    # SIGNATURE
    # player_color :: Board, String => String
    def player_color(self, color):
        colors = ("black", "white")
        if self.color == colors[1]:
            self.color = colors[0]
        elif self.color == colors[0]:
            self.color = colors[1]
        return self.color

    # PURPOSE
    # Handles mouse clicks on the board. x is the x-coordinate of the mouse
    # click and y is the y-coordinate of the mouse click.
    # This function also draws the piece if the Records class determines that
    # the click results in a valid move.
    # SIGNATURE
    # board_click :: Board, Float, Float => None
    def board_click(self, x, y):
        x = self.correct_coordinate(x)
        y = self.correct_coordinate(y)
        proposed_move = [x, y]
        # If click it outside of the boundaries of the board, do nothing.
        if x == -1 or y == -1:
            pass
        else:
            records = Records(self.size, proposed_move, self)
            records_check = records.record_validation(proposed_move)
            if records_check is False:
                pass
            else:
                self.draw_piece(x, y, self.player_color(self.color))
                for entry in records.will_flip:
                    x_coord = entry[0]
                    y_coord = entry[1]
                    self.draw_piece(x_coord, y_coord, self.color)
                print("End of {}'s turn!".format(self.color))
                # If no more open squares, will announce the winner.
                if records.is_winner() is True:
                    print(records.winner_is(records.score_counter("black"),
                          records.score_counter("white")))
                    self.black_score = records.score_counter("black")
                    self.winner_declared = True
                    turtle.bye()
                else:
                    print("Current score - Black: {}, White: {}".format
                          (records.score_counter("black"),
                           records.score_counter("white")))
                    self.ai_move()

    # PURPOSE
    # Handles the AI plays after a valid player move. AI will always play the
    # move indicated in Records that has the best outcome. Also includes some
    # validation to indicate if there are no moves available for either player,
    # and if there is a winner to declare. If the game is over, it will set
    # variables that can be referenced in main to know that the game is over
    # and the score of the player.
    # SIGNATURE
    # ai_move :: Board => None
    def ai_move(self):
        wht_default = Records(self.size, [0, 0], self)
        # If there are no white moves available after a valid black turn.
        if wht_default.turns_avail is False:
            print("No white moves available!")
            wht_default.add_skipped_turn()
            self.player_color(self.color)
            blk_test = Records(self.size, [0, 0], self)
            # If no more possible black moves, declare a winner.
            if blk_test.turns_avail is False:
                print("No black moves available!")
                print(blk_test.winner_is(blk_test.score_counter("black"),
                      blk_test.score_counter("white")))
                self.black_score = blk_test.score_counter("black")
                self.winner_declared = True
                turtle.bye()
        else:
            to_play = wht_default.best_move
            wht_default.play_ai_move(to_play)
            print("White is thinking.....")
            print("White plays coordinate {}".format(to_play[:2]))
            self.comp_draw_piece(to_play)
            for entry in wht_default.will_flip:
                x_coord = entry[0]
                y_coord = entry[1]
                self.draw_piece(x_coord, y_coord, self.color)
            # If no more open squares after white plays, declare a winner.
            if wht_default.is_winner() is True:
                print(wht_default.winner_is(wht_default.score_counter("black"),
                      wht_default.score_counter("white")))
                self.black_score = wht_default.score_counter("black")
                self.winner_declared = True
                turtle.bye()
            else:
                print("Current score - Black: {}, White: {}".format
                      (wht_default.score_counter("black"),
                       wht_default.score_counter("white")))
                blk_test = Records(self.size, [0, 0], self)
                # After white goes, determine if there is a possible black move.
                if blk_test.turns_avail is False:
                    print("No black moves available!")
                    blk_test.add_skipped_turn()
                    self.player_color(self.color)
                    white_test = Records(self.size, [0, 0], self)
                    # If no possible black or white move, declare a winner.
                    if white_test.turns_avail is False:
                        print("No white moves available!")
                        print(white_test.winner_is(white_test.score_counter
                              ("black"), white_test.score_counter("white")))
                        self.black_score = white_test.score_counter("black")
                        self.winner_declared = True
                        turtle.bye()
                    else:
                        self.ai_move()

    # PURPOSE
    # Draws the piece on the board without a click - used for AI/white pieces.
    # Takes coordinate input determined in records class to draw piece.
    # SIGNATURE
    # comp_draw_piece :: Board, List[Int, Int, Str] => None
    def comp_draw_piece(self, coord):
        x = coord[0]
        y = coord[1]
        self.draw_piece(x, y, self.player_color(self.color))
        print("End of {}'s turn!".format(self.color))

    # PURPOSE
    # Helper function returning the center coordinates of a square on the board.
    # SIGNATURE
    # get_center :: Board, Integer, Integer => (Float, Float)
    def get_center(self, col, row):
        x = col * SQUARE + SQUARE + self.low_bound
        y = (row * SQUARE + SQUARE / 2) + self.low_bound
        return (x, y)

    # PURPOSE
    # Draw a tile of a given color in a given square on the board.
    # SIGNATURE
    # draw_piece :: Board, Integer, Integer, String => None
    def draw_piece(self, col, row, color):
        center = self.get_center(col, row)
        RADIUS = SQUARE // 2
        self.othello.penup()
        self.othello.setposition(center[0], center[1])
        self.othello.pendown()
        self.othello.begin_fill()
        self.othello.color("black", color)
        self.othello.circle(RADIUS)
        self.othello.end_fill()

    # PURPOSE
    # Helper function that draws the 4 starting pieces.
    # SIGNATURE
    # start_pieces :: Board => None
    def start_pieces(self):
        # Puts the start pieces in position
        self.draw_piece(self.size // 2 - 1, self.size // 2, "white")
        self.draw_piece(self.size // 2, self.size // 2, "black")
        self.draw_piece(self.size // 2 - 1, self.size // 2 - 1, "black")
        self.draw_piece(self.size // 2, self.size // 2 - 1, "white")
