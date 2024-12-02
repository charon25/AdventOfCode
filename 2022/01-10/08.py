with open('../inputs/08.txt', 'r') as fi:
    lines = fi.read().splitlines()

WIDTH = len(lines[0])
HEIGHT = len(lines)

height_map = [list(map(int, line)) for line in lines]

# Part 1
visible_trees = set()

for direction in (1, -1):
    for x in range(WIDTH):
        y = 0 if direction == 1 else HEIGHT - 1
        height = -1
        while height < 9 and ((direction == 1 and y < HEIGHT) or (direction == -1 and 0 <= y)):
            if height_map[y][x] > height:
                height = height_map[y][x]
                visible_trees.add((x, y))
            y += direction

for direction in (1, -1):
    for y in range(HEIGHT):
        x = 0 if direction == 1 else WIDTH - 1
        height = -1
        while height < 9 and ((direction == 1 and x < WIDTH) or (direction == -1 and 0 <= x)):
            if height_map[y][x] > height:
                height = height_map[y][x]
                visible_trees.add((x, y))
            x += direction

print(len(visible_trees))

# Part 2
best_score = 0

for y in range(1, HEIGHT - 1):
    for x in range(1, WIDTH - 1):
        score = 1
        height = height_map[y][x]
        # Up
        y_t = y - 1
        while 0 < y_t and height_map[y_t][x] < height:
            y_t -= 1
        score *= (y - y_t)
        # Down
        y_t = y + 1
        while y_t < HEIGHT - 1 and height_map[y_t][x] < height:
            y_t += 1
        score *= (y_t - y)
        # Left
        x_t = x - 1
        while 0 < x_t and height_map[y][x_t] < height:
            x_t -= 1
        score *= (x - x_t)
        # Left
        x_t = x + 1
        while x_t < WIDTH - 1 and height_map[y][x_t] < height:
            x_t += 1
        score *= (x_t - x)

        if score > best_score:
            best_score = score

print(best_score)
