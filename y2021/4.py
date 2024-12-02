def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(4)
boards = []
marked = []

numbers = list(map(int, lines[0].split(',')))
board = []
for i, line in enumerate(lines[2:]):
    if i % 6 == 5:
        boards.append(board)
        marked.append([[0, 0, 0, 0, 0] for _ in range(5)])
        board = []
    else:
        board.append(list(map(int, line.split())))

boards.append(board)
marked.append([[0, 0, 0, 0, 0] for _ in range(5)])
board = []

won = [0 for _ in range(len(boards))]

score = 0
def has_won(b_ind):
    board = marked[b_ind]
    s_cols = [0 for i in range(5)]
    for r_ind, row in enumerate(board):
        if sum(row) == 5:
            return True
        for c_ind, col in enumerate(row):
            s_cols[c_ind] += col
    return any([s == 5 for s in s_cols])

for n in numbers:
    for b_ind, board in enumerate(boards):
        for r_ind, row in enumerate(board):
            for c_ind, col in enumerate(row):
                if n == col:
                    marked[b_ind][r_ind][c_ind] = 1

    for i in range(len(won)):
        if won[i] == 0 and has_won(i):
            won[i] = 1
            if sum(won) == len(won):
                score = 0
                for r_ind, row in enumerate(boards[i]):
                    for c_ind, col in enumerate(row):
                        score += (1 - marked[i][r_ind][c_ind]) * col
                score *= n
    if sum(won) == len(won):
        break

    # for b_ind, board in enumerate(boards):
    #     if has_won(b_ind):
    #         score = 0
    #         for r_ind, row in enumerate(board):
    #             for c_ind, col in enumerate(row):
    #                 score += (1 - marked[b_ind][r_ind][c_ind]) * col
    #         score *= n
    # if score != 0:
    #     break

print(score)
    
