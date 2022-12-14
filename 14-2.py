with open('inputs/14.txt', 'r') as fi:
    lines = fi.read().splitlines()

paths: list[list[tuple[int, int]]] = []

min_x = 10**10
max_x = 0
max_y = 0

for line in lines:
    coords = line.split(' -> ')
    paths.append([])
    for xy in coords:
        x, y = map(int, xy.split(','))
        min_x = min(x, min_x)
        max_x = max(x, max_x)
        max_y = max(y, max_y)
        paths[-1].append((x, y))

HEIGHT = max_y + 3
WIDTH = max(max_x - min_x + 1, 2 * HEIGHT + 10)
X_OFFSET = min_x - (WIDTH - (max_x - min_x + 1)) // 2

terrain = [[0 for _ in range(WIDTH)] for __ in range(HEIGHT)]

for path in paths:
    for (x1, y1), (x2, y2) in zip(path, path[1:]):
        if x1 == x2:
            start, end = min(y1, y2), max(y1, y2)
            for y in range(start, end + 1):
                terrain[y][x1 - X_OFFSET] = 1
        elif y1 == y2:
            start, end = min(x1, x2), max(x1, x2)
            for x in range(start, end + 1):
                terrain[y1][x - X_OFFSET] = 1

terrain[0][500 - X_OFFSET] = 3

for x in range(WIDTH):
    terrain[HEIGHT - 1][x] = 1

x_sand = y_sand = None

sand_fallen = 0

while True:
    if x_sand is None:
        x_sand = 500 - X_OFFSET
        y_sand = 0
    else:
        if terrain[y_sand + 1][x_sand] == 0:
            y_sand += 1
        elif terrain[y_sand + 1][x_sand - 1] == 0:
            y_sand += 1
            x_sand -= 1
        elif terrain[y_sand + 1][x_sand + 1] == 0:
            y_sand += 1
            x_sand += 1
        else:
            if terrain[y_sand][x_sand] == 3:
                sand_fallen += 1
                break
            terrain[y_sand][x_sand] = 2
            x_sand = y_sand = None
            sand_fallen += 1

print(sand_fallen)
