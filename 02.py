with open('inputs/02.txt', 'r') as fi:
    lines = fi.read().splitlines()

SCORES = {'X': 1, 'Y': 2, 'Z': 3}
WINS = {'A': {'X': 3, 'Y': 6, 'Z': 0}, 'B': {'X': 0, 'Y': 3, 'Z': 6}, 'C': {'X': 6, 'Y': 0, 'Z': 3}}

ACTION = {'X': {'A': 'Z', 'B': 'X', 'C': 'Y'}, 'Y': {'A': 'X', 'B': 'Y', 'C': 'Z'}, 'Z': {'A': 'Y', 'B': 'Z', 'C': 'X'}}

total_score = 0
total_score_2 = 0
for line in lines:
    enemy, player = line.split(' ')
    total_score += SCORES[player] + WINS[enemy][player]

    player = ACTION[player][enemy]
    total_score_2 += SCORES[player] + WINS[enemy][player]

# Part 1
print(total_score)

# Part 2
print(total_score_2)
