
from board import Board


def test_getcenter():
    b = Board(4)
    assert(b.get_center(0, 0) == (-100.0, -150.0))
    assert(b.get_center(1, 0) == (0.0, -150.0))
    assert(b.get_center(2, 0) == (100.0, -150.0))
    assert(b.get_center(3, 3) == (200.0, 150.0))


def test_correct_coordinate():
    b = Board(8)
    assert(b.correct_coordinate(-390) == 0)
    assert(b.correct_coordinate(-290) == 1)
    assert(b.correct_coordinate(-190) == 2)
    assert(b.correct_coordinate(-90) == 3)
    assert(b.correct_coordinate(90) == 4)
    assert(b.correct_coordinate(190) == 5)
    assert(b.correct_coordinate(290) == 6)
    assert(b.correct_coordinate(390) == 7)


def test_player_color():
    b = Board(8)
    assert(b.player_color("white") == "black")
    assert(b.player_color("black") == "white")
