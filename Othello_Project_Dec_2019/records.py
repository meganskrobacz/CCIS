
MASTER_LIST = [[3, 4, 'white'], [4, 4, 'black'], [3, 3, 'black'],
               [4, 3, 'white']]
SKIPPED_TURNS = 0


class Records:
    # PURPOSE
    # Constructor for the Records class.
    # SIGNATURE
    # __init__ :: Records, Integer, List[Integers], Board => Records
    def __init__(self, size, new_coord, board):
        self.size = size
        self.total_pieces = size * size
        self.board = board
        self.new_coord = new_coord
        self.plays_used = MASTER_LIST
        self.plays_used_coord_only = self.color_stripper(self.plays_used)
        self.skipped_turns = SKIPPED_TURNS
        self.spaces_available = self.available_moves(self.size, MASTER_LIST)
        self.color = self.player_color()
        self.comp_color = self.opponent_color()
        self.total_flips_avail = []
        self.legal_moves_avail = \
            self.determine_legal_moves(self.spaces_available)
        self.turns_avail = self.can_play()
        self.best_move = self.ideal_move()
        self.best_move_index = self.index_of_best_move()

    # PURPOSE
    # Takes the coordinates of the squares still open and runs a loop through
    # them to determine which, if any, would be legal moves for the player
    # this instance is referring to. Returns a nested list of possible
    # valid moves.
    # SIGNATURE
    # determine_legal_moves :: Records, List[[Int, Int]] => List[[Int, Int]]
    def determine_legal_moves(self, spaces_avail):
        legal_moves = []
        for entry in spaces_avail:
            occupied_spaces_around = self.is_occupied(self.box_around(entry))
            if len(occupied_spaces_around) > 0:
                tiles_with_opp_tiles = \
                    self.is_next_to_opp(occupied_spaces_around)
                directions = self.what_direction(tiles_with_opp_tiles, entry)
                tiles_to_flip = self.removes_blanks(self.moves_outcomes
                                                    (directions,
                                                     tiles_with_opp_tiles))
                if len(tiles_to_flip) > 0:
                    legal_moves.append(entry)
                    self.total_flips_avail.append(tiles_to_flip)
        return legal_moves

    # PURPOSE
    # Determines if it's possible for a move to be played.
    # SIGNATURE
    # can_play :: Records => Boolean
    def can_play(self):
        if len(self.legal_moves_avail) == 0:
            return False
        else:
            return True

    # PURPOSE
    # Increases the SKIPPED_TURNS global variable by one.
    # SIGNATURE
    # add_skipped_turn :: Records => None
    def add_skipped_turn(self):
        global SKIPPED_TURNS
        if self.turns_avail is False:
            SKIPPED_TURNS = SKIPPED_TURNS + 1

    # PURPOSE
    # Based on the list of legal moves generated previously, this function
    # is designed to figure out the legal move that will result in the most
    # amount of pieces flipped/scored, and report that move. If there are no
    # legal moves available, it will report None to prevent an error.
    # SIGNATURE
    # ideal_move :: Records => None or List[Int, Int]
    def ideal_move(self):
        if self.turns_avail is False:
            return None
        else:
            current_index = 0
            for i, entry in enumerate(self.total_flips_avail):
                if len(entry) > current_index:
                    current_index = i
            best_move = self.legal_moves_avail[current_index]
            return best_move

    # PURPOSE
    # Helper function that validates whether there is a winner based on how
    # many plays left. Need to at some point incorporate here that winner if
    # there are no more available moves after two turns.
    # SIGNATURE
    # is_winner :: Records => Boolean
    def is_winner(self):
        if len(self.plays_used) == self.total_pieces:
            return True
        else:
            return False

    # PURPOSE
    # Helper function that figures out the score based on the color input
    # SIGNATURE
    # score_counter :: Records, String => Integer
    def score_counter(self, color):
        score = 0
        for play in self.plays_used:
            score += play.count(color)
        return score

    # PURPOSE
    # Helper function that reports who the winner of the game is.
    # SIGNATURE
    # winner_is :: Records => String
    def winner_is(self, black_score, white_score):
        if black_score == white_score:
            return "Tie! Each player has {} points.".format(black_score)
        elif max(black_score, white_score) == black_score:
            return "Black wins with {} points, defeating white " \
                   "with only {} points!".format(black_score, white_score)
        else:
            return "White wins with {} points, defeating black with only " \
                   "{} points!".format(white_score, black_score)

    # PURPOSE
    # Validates that the square is unoccupied before moving forward.
    # If the end result will be True, also runs through a plethora of other
    # functions to add records for future moves before returning.
    # SIGNATURE
    # record_validation :: Records => Boolean
    def record_validation(self, coord):
        found = 0
        correct_index = 0
        for i, entry in enumerate(self.legal_moves_avail):
            if entry == coord:
                found += 1
                correct_index = i
        if found == 0:
            return False
        else:
            entry = self.coord_and_color(self.color, coord)
            self.add_play(entry, MASTER_LIST)
            self.add_play(coord, self.plays_used_coord_only)
            self.will_flip = self.total_flips_avail[correct_index]
            self.tile_flipper(self.will_flip)
            return True

    # PURPOSE
    # Plays a move called by the AI.
    # SIGNATURE
    # play_ai_move :: Records, List[Int, Int] => None
    def play_ai_move(self, coord):
        entry = self.coord_and_color(self.color, coord)
        self.add_play(entry, MASTER_LIST)
        self.add_play(coord, self.plays_used_coord_only)
        self.will_flip = self.total_flips_avail[self.best_move_index]
        self.tile_flipper(self.will_flip)

    # PURPOSE
    # Figures out the index of the best move in the list of legal moves.
    # SIGNATURE
    # index_of_best_move :: Records => None or Int
    def index_of_best_move(self):
        if self.turns_avail is False:
            return None
        else:
            return self.legal_moves_avail.index(self.best_move)

    # PURPOSE
    # Helper function which switches the colors of the tiles to be recorded.
    # Odd numbers of turns should result in a white record, even a black.
    # SIGNATURE
    # player_color :: Records => String
    def player_color(self):
        colors = ["black", "white"]
        if (len(self.plays_used) + self.skipped_turns) % 2 == 1:
            return colors[1]
        else:
            return colors[0]

    # PURPOSE
    # Determines the opponent color based on player color.
    # SIGNATURE
    # opponent_color :: Record => String
    def opponent_color(self):
        colors = ("black", "white")
        if self.color == colors[1]:
            return colors[0]
        elif self.color == colors[0]:
            return colors[1]

    # PURPOSE
    # Helper function which creates a single entry to be added onto the
    # list of played turns.
    # SIGNATURE
    # coord_and_color :: Records, String, List[Int, Int] => List[Int, Int, Str]
    def coord_and_color(self, color, new_coord):
        new_entry = new_coord
        new_entry.append(color)
        return new_entry

    # PURPOSE
    # Helper function which adds a new entry to a list.
    # Designed to be used for many different lists kept in this class.
    # SIGNATURE
    # add_play :: Records, List[Any], List[[Any]] ==> List[[Any]]
    def add_play(self, entry, lst):
        lst.append(entry)
        return lst

    # PURPOSE
    # Creates a nested list of coodinates on the board still open.
    # SIGNATURE
    # available_moves :: Records, Int, List[[Int, Int, Str]] => None
    def available_moves(self, size, plays_used):
        var_list = []
        spaces_avail = []
        for i in range(size):
            var_list.append(i)
        used_coordinates = self.color_stripper(plays_used)
        for num1 in var_list:
            for num2 in var_list:
                if [num1, num2] not in used_coordinates:
                    spaces_avail.append([num1, num2])
        return spaces_avail

    # PURPOSE
    # Strips the color variable from the master list for comparison with
    # other functions that simply need coordinates. Results are used in other
    # functions.
    # SIGNATURE
    # color_stripper :: Records, List[[Int, Int, Str]] => List[[Int, Int]]
    def color_stripper(self, lst):
        coordinates_only = []
        for entry in lst:
            coordinates = entry[:2]
            coordinates_only.append(coordinates)
        return coordinates_only

    # PURPOSE
    # Generates a nested list of coordinates to check around a proposed move.
    # SIGNATURE
    # box_around :: Records, List[Int, Int] => List[[Int, Int]]
    def box_around(self, coord):
        COORD_MAX = self.size - 1
        COORD_MIN = 0
        x = coord[0]
        y = coord[1]
        spaces_around = []
        top_lft_cnr = [x - 1, y + 1]
        lft_side = [x - 1, y]
        bot_lft_cnr = [x - 1, y - 1]
        top_side = [x, y + 1]
        bot_side = [x, y - 1]
        top_rht_cnr = [x + 1, y + 1]
        rht_side = [x + 1, y]
        bot_rht_cnr = [x + 1, y - 1]
        if x > COORD_MIN and x < COORD_MAX:
            if y == COORD_MIN:
                to_add = [top_lft_cnr, lft_side, top_side, top_rht_cnr,
                          rht_side]
            elif y == COORD_MAX:
                to_add = [lft_side, bot_lft_cnr, bot_side, rht_side,
                          bot_rht_cnr]
            else:
                to_add = [top_lft_cnr, lft_side, bot_lft_cnr, top_side,
                          bot_side, top_rht_cnr, rht_side, bot_rht_cnr]
        elif x == COORD_MIN:
            if y == COORD_MIN:
                to_add = [top_side, top_rht_cnr, rht_side]
            elif y == COORD_MAX:
                to_add = [bot_side, rht_side, bot_rht_cnr]
            else:
                to_add = [top_side, bot_side, top_rht_cnr, rht_side,
                          bot_rht_cnr]
        elif x == COORD_MAX:
            if y == COORD_MIN:
                to_add = [top_lft_cnr, lft_side, top_side]
            elif y == COORD_MAX:
                to_add = [lft_side, bot_lft_cnr, bot_side]
            else:
                to_add = [top_lft_cnr, lft_side, bot_lft_cnr, top_side,
                          bot_side]
        for i in to_add:
            spaces_around.append(i)
        return spaces_around

    # PURPOSE
    # Takes the nested list of surrounding squares calculated in box_around and
    # pulls out only the ones that are occupied. This nested list is then
    # used in another function to determine if the occupied squares would lead
    # to a valid move.
    # SIGNATURE
    # is_occupied :: Records, List[[Int, Int]] => List[[Int, Int, Str]]
    def is_occupied(self, spaces_around):
        occupied_squares = []
        for entry in spaces_around:
            if entry in self.plays_used_coord_only:
                index = self.plays_used_coord_only.index(entry)
                with_color = self.plays_used[index]
                occupied_squares.append(with_color)
        return occupied_squares

    # PURPOSE
    # Takes the nested list of coordinates that are occupied around a proposed
    # move and narrows it further to only include coordinates of pieces that
    # are of the opposing color.
    # SIGNATURE
    # is_next_to_opp :: Records, List[[Int, Int, Str]] => List[[Int, Int, Str]]
    def is_next_to_opp(self, is_occupied_lst):
        occupied_by_other_color = []
        for entry in is_occupied_lst:
            color = entry[-1]
            if color == self.comp_color:
                occupied_by_other_color.append(entry)
        return occupied_by_other_color

    # PURPOSE
    # Based on the list of available moves, figures out what direction they are
    # in in relation to the original piece. Output will be a nested list of two
    # integers for each entry which indicate how much to add to the original
    # coordinates.
    # SIGNATURE
    # what_direction :: Records, List[[Int, Int, Str]], List[Int, Int]
    def what_direction(self, is_next_to_opp_lst, coord):
        directions = []
        for entry in is_next_to_opp_lst:
            x_possible_move = entry[0]
            x_proposed_move = coord[0]
            y_possible_move = entry[1]
            y_proposed_move = coord[1]
            x_dir_change = x_possible_move - x_proposed_move
            y_dir_change = y_possible_move - y_proposed_move
            x_and_y_diff = [x_dir_change, y_dir_change]
            directions.append(x_and_y_diff)
        return directions

    # PURPOSE
    # Creates a nested list of the outcomes for all possible moves of a click.
    # SIGNATURE
    # moves_outcomes :: Records, List[[Int, Int, Str]] => List[[Int, Int, Str]]
    def moves_outcomes(self, what_dir_lst, is_next_to_opp_lst):
        will_flip = []
        if len(is_next_to_opp_lst) == 0:
            will_flip.append([])
        else:
            for index, entry in enumerate(is_next_to_opp_lst):
                move = self.to_flip(what_dir_lst[index], entry)
                if len(move) == 0:
                    pass
                elif move[-1] == ["Valid move"]:
                    move.pop(-1)
                    if move not in will_flip:
                        will_flip.extend(move)
        return will_flip

    # PURPOSE
    # Takes the given direction and runs through a list of coordinates that are
    # in that direction. Keeps going until it runs into a space occupied by
    # the player color/piece, or an empty space. This will be helpful in
    # determining whether the move is valid or not, and for generating a list of
    # pieces to flip.
    # to_flip :: Records, List[[Int, Int]], List[[Int, Int, Str]] =>
    #            List[[Int, Int, Str]]
    def to_flip(self, what_dir_lst, coord_to_start):
        MAX = 7
        MIN = 0
        x_new = what_dir_lst[0] + coord_to_start[0]
        y_new = what_dir_lst[1] + coord_to_start[1]
        to_add = []
        # If new coord is outside of range of board.
        if x_new > MAX or x_new < MIN or y_new > MAX or y_new < MIN:
            return to_add
        new_coord = [x_new, y_new, self.color]
        # If new coord is an unoccupied space.
        if new_coord[:2] not in self.plays_used_coord_only:
            return to_add
        # If new coord is occupied by a piece of the player color - end loop.
        elif new_coord in MASTER_LIST:
            to_add.append(coord_to_start)
            to_add.append(["Valid move"])
            return to_add
        # Space is occupied by a piece that would flip - add to list.
        else:
            to_add.append(coord_to_start)
            return to_add + self.to_flip(what_dir_lst, new_coord)

    # PURPOSE
    # Removes blanks in a nested list, so that only entries with values remain.
    # SIGNATURE
    # removes_blanks :: Records, List[[Any]] => List[[Any]]
    def removes_blanks(self, lst):
        correct_list = []
        for entry in lst:
            if len(entry) > 0:
                correct_list.append(entry)
        return correct_list

    # PURPOSE
    # Flips tiles to other color in all lists which keep records.
    # SIGNATURE
    # tile_flipper :: Records, List[[Int, Int, Str]] => None
    def tile_flipper(self, tiles_to_flip):
        for entry in tiles_to_flip:
            coord = entry[:2]
            index = self.plays_used_coord_only.index(coord)
            coord.append(self.color)
            self.add_play(coord, MASTER_LIST)
            MASTER_LIST.pop(index)
            self.plays_used_coord_only = self.color_stripper(self.plays_used)
