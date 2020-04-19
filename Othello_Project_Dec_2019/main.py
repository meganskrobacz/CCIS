
from board import Board


# PURPOSE
# Sorts the items in a list
# SIGNATURE
# bubble_sort :: List[[Str, Int]] => List[[String, Int]]
def bubble_sort(lst):
    length = len(lst)
    for i in range(length):
        for j in range(length - i - 1):
            if lst[j][1] < lst[(j + 1)][1]:
                temp = lst[j]
                lst[j] = lst[(j + 1)]
                lst[(j + 1)] = temp
    return lst


def main():
    b = Board(8)
    b.draw_board()
    scores_unsorted = []
    if b.winner_declared is True:
        name = input(str("Enter your name for the scoreboard: "))
        indiv_score = b.black_score
        new_entry = [name, indiv_score]
        try:
            file = open("scores.txt", "r")
            for line in file:
                line.strip()
                lst = line.split(" ")
                name = lst[0]
                score = int(lst[1])
                to_add = [name, score]
                scores_unsorted.append(to_add)
            file.close()
        except FileNotFoundError:
            pass
        scores_unsorted.append(new_entry)
        sorted_list = bubble_sort(scores_unsorted)
        file = open("scores.txt", "w")
        for entry in sorted_list:
            file.writelines("{} {}\n".format(entry[0], entry[1]))
        file.close()
        print("Here is the leaderboard:")
        for entry in sorted_list:
            name = entry[0]
            score = entry[1]
            not_a_list = "{} {}".format(name, score)
            print(not_a_list)


main()
