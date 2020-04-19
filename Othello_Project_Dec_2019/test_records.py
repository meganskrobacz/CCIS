
from board import Board
from records import Records


def test_is_winner():
    r = Records(2, [0, 0], Board(2))
    r.plays_used = [[0, 0, "white"]]
    r.total_pieces = 2
    assert(r.is_winner() is False)
    r.plays_used = [[0, 0, "white"], [0, 1, "black"]]
    assert(r.is_winner() is True)


def test_score_counter():
    r = Records(2, [0, 0], Board(2))
    r.plays_used = [[0, 0, "white"], [0, 1, "black"], [1, 1, "white"]]
    assert(r.score_counter("white") == 2)
    assert(r.score_counter("black") == 1)
    assert(r.score_counter("red") == 0)


def test_winner_is():
    r = Records(2, [0, 0], Board(2))
    assert(r.winner_is(0, 0) == "Tie! Each player has 0 points.")
    assert(r.winner_is(3, 2) == "Black wins with 3 points, defeating white "
           "with only 2 points!")
    assert(r.winner_is(2, 3) == "White wins with 3 points, defeating black "
           "with only 2 points!")


def test_player_color():
    r = Records(2, [0, 0], Board(2))
    r.plays_used = [[0, 0, "white"], [0, 1, "black"], [1, 1, "white"]]
    r.skipped_turns = 0
    assert(r.player_color() == "white")
    r.skipped_turns = 1
    assert(r.player_color() == "black")


def test_coord_and_color():
    r = Records(2, [0, 0], Board(2))
    assert(r.coord_and_color("black", [0, 0]) == [0, 0, "black"])
    assert(r.coord_and_color("white", [0, 0]) == [0, 0, "white"])


def test_add_play():
    r = Records(2, [0, 0], Board(2))
    assert(r.add_play([1, 0], [[0, 0]]) == [[0, 0], [1, 0]])
    assert(r.add_play([1, 0], [[0, 0], [1, 1]]) == [[0, 0], [1, 1], [1, 0]])


def test_color_stripper():
    r = Records(2, [0, 0], Board(2))
    assert(r.color_stripper([[0, 0, "black"], [0, 1, "white"]]) == [[0, 0],
           [0, 1]])
    assert(r.color_stripper([[0, 0, "black"]]) == [[0, 0]])


def test_opponent_color():
    r = Records(2, [0, 0], Board(2))
    r.color = "black"
    assert(r.opponent_color() == "white")
    r.color = "white"
    assert(r.opponent_color() == "black")


def test_box_around():
    r = Records(8, [0, 0], Board(8))
    assert(r.box_around([0, 0]) == [[0, 1], [1, 1], [1, 0]])
    assert(r.box_around([0, 1]) == [[0, 2], [0, 0], [1, 2], [1, 1], [1, 0]])
    assert(r.box_around([0, 7]) == [[0, 6], [1, 7], [1, 6]])
    assert(r.box_around([3, 4]) == [[2, 5], [2, 4], [2, 3], [3, 5], [3, 3],
           [4, 5], [4, 4], [4, 3]])
    assert(r.box_around([1, 7]) == [[0, 7], [0, 6], [1, 6], [2, 7], [2, 6]])
    assert(r.box_around([1, 0]) == [[0, 1], [0, 0], [1, 1], [2, 1], [2, 0]])
    assert(r.box_around([7, 0]) == [[6, 1], [6, 0], [7, 1]])
    assert(r.box_around([7, 1]) == [[6, 2], [6, 1], [6, 0], [7, 2], [7, 0]])
    assert(r.box_around([7, 7]) == [[6, 7], [6, 6], [7, 6]])


def test_is_occupied():
    r = Records(8, [0, 0], Board(8))
    r.plays_used_coord_only = [[0, 0]]
    r.plays_used = [[0, 0, "white"]]
    assert(r.is_occupied([[0, 1], [1, 1], [1, 0]]) == [])
    assert(r.is_occupied([[0, 1], [0, 0], [1, 1], [2, 1], [2, 0]]) ==
           [[0, 0, "white"]])


def test_is_next_to_opp():
    r = Records(8, [0, 0], Board(8))
    r.comp_color = "white"
    assert(r.is_next_to_opp([[0, 0, "white"]]) == [[0, 0, "white"]])
    assert(r.is_next_to_opp([[0, 0, "black"]]) == [])


def test_what_direction():
    r = Records(8, [0, 0], Board(8))
    assert(r.what_direction([[3, 4, 'white']], [2, 3]) == [[1, 1]])
    assert(r.what_direction([], [2, 2]) == [])


def test_determine_legal_moves():
    r = Records(8, [0, 0], Board(8))
    r.plays_used = [[3, 4, 'white'], [4, 4, 'black'], [3, 3, 'black'],
                    [4, 3, 'white']]
    r.plays_used_coord_only = [[3, 4], [4, 4], [3, 3], [4, 3]]
    r.color = "black"
    r.comp_color = "white"
    assert(r.determine_legal_moves([[0, 0], [0, 1], [0, 2], [0, 3], [0, 4],
           [0, 5], [0, 6], [0, 7], [1, 0], [1, 1], [1, 2], [1, 3], [1, 4],
           [1, 5], [1, 6], [1, 7], [2, 0], [2, 1], [2, 2], [2, 3], [2, 4],
           [2, 5], [2, 6], [2, 7], [3, 0], [3, 1], [3, 2], [3, 5], [3, 6],
           [3, 7], [4, 0], [4, 1], [4, 2], [4, 5], [4, 6], [4, 7], [5, 0],
           [5, 1], [5, 2], [5, 3], [5, 4], [5, 5], [5, 6], [5, 7], [6, 0],
           [6, 1], [6, 2], [6, 3], [6, 4], [6, 5], [6, 6], [6, 7], [7, 0],
           [7, 1], [7, 2], [7, 3], [7, 4], [7, 5], [7, 6], [7, 7]]) ==
           [[2, 4], [3, 5], [4, 2], [5, 3]])


def test_index_of_best_move():
    r = Records(8, [0, 0], Board(8))
    r.best_move = [0, 0]
    r.legal_moves_avail = [[0, 0], [0, 1]]
    r.turns_avail = False
    assert(r.index_of_best_move() is None)
    r.turns_avail = True
    assert(r.index_of_best_move() == 0)


def test_available_moves():
    r = Records(8, [0, 0], Board(8))
    assert(r.available_moves(8, [[3, 4, 'white'], [4, 4, 'black'],
           [3, 3, 'black'], [4, 3, 'white']]) == [[0, 0], [0, 1], [0, 2],
           [0, 3], [0, 4], [0, 5], [0, 6], [0, 7], [1, 0], [1, 1], [1, 2],
           [1, 3], [1, 4], [1, 5], [1, 6], [1, 7], [2, 0], [2, 1], [2, 2],
           [2, 3], [2, 4], [2, 5], [2, 6], [2, 7], [3, 0], [3, 1], [3, 2],
           [3, 5], [3, 6], [3, 7], [4, 0], [4, 1], [4, 2], [4, 5], [4, 6],
           [4, 7], [5, 0], [5, 1], [5, 2], [5, 3], [5, 4], [5, 5], [5, 6],
           [5, 7], [6, 0], [6, 1], [6, 2], [6, 3], [6, 4], [6, 5], [6, 6],
           [6, 7], [7, 0], [7, 1], [7, 2], [7, 3], [7, 4], [7, 5], [7, 6],
           [7, 7]])


def test_moves_outcomes():
    r = Records(8, [0, 0], Board(8))
    r.color = "black"
    assert(r.moves_outcomes([], []) == [[]])
    assert(r.moves_outcomes([[1, 1]], [[3, 4, "white"]]) == [])
    assert(r.moves_outcomes([[1, 0]], [[3, 4, "white"]]) == [[3, 4, "white"]])


def test_to_flip():
    r = Records(8, [0, 0], Board(8))
    r.color = "black"
    assert(r.to_flip([1, 1], [3, 4, "white"]) == [])
    assert(r.to_flip([1, 1], [7, 7, "white"]) == [])
    assert(r.to_flip([1, 0], [3, 4, "white"]) == [[3, 4, "white"],
           ["Valid move"]])


def test_removes_blanks():
    r = Records(8, [0, 0], Board(8))
    assert(r.removes_blanks([[], [1, 2]]) == [[1, 2]])
    assert(r.removes_blanks([[1, 2]]) == [[1, 2]])


def test_can_play():
    r = Records(8, [0, 0], Board(8))
    r.legal_moves_avail = []
    assert(r.can_play() is False)
    r.legal_moves_avail = [[1, 2, "white"]]
    assert(r.can_play() is True)


def test_ideal_move():
    r = Records(8, [0, 0], Board(8))
    r.turns_avail = False
    assert(r.ideal_move() is None)
    r.turns_avail = True
    r.legal_moves_avail = [[1, 6], [2, 4], [2, 6], [3, 7]]
    r.total_flips_avail = [[[2, 5, 'white'], [3, 4, 'black']],
                           [[3, 4, 'white']], [[3, 5, 'white']],
                           [[3, 6, 'white'], [3, 5, 'black'], [3, 4, 'black']]]
    assert(r.ideal_move() == [3, 7])
