def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(10)

BRACKETS = {'(': ')', '[': ']', '{': '}', '<': '>'}
# POINTS = {')': 3, ']': 57, '}': 1197, '>': 25137}
POINTS = {')': 1, ']': 2, '}': 3, '>': 4}

# score = 0
scores = []
for line in lines:
    stack = []
    corrupted = False
    for c in line:
        if c in '([{<':
            stack.append(c)
        else:
            opening = stack.pop()
            if BRACKETS[opening] != c:
                # score += POINTS[c]
                corrupted = True
                break
    if not corrupted:
        score = 0
        for c in reversed(stack):
            c = BRACKETS[c]
            score = 5 * score + POINTS[c]
        scores.append(score)

scores.sort()
print(scores[len(scores)//2])