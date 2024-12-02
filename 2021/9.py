def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(9)
heights = [list(map(int, line)) for line in lines]
LEN_X = len(heights[0])
LEN_Y = len(heights)
import functools

@functools.lru_cache(maxsize=None)
def get_neighbors(x, y):
    neighbors = []
    if x > 0:
        neighbors.append((x - 1, y))
    if x < LEN_X - 1:
        neighbors.append((x + 1, y))
    if y > 0:
        neighbors.append((x, y - 1))
    if y < LEN_Y - 1:
        neighbors.append((x, y + 1))
    return neighbors

low_points = []
for y in range(LEN_Y):
    for x in range(LEN_X):
        val = int(heights[y][x])
        if all(val < heights[ny][nx] for nx, ny in get_neighbors(x, y)):
            low_points.append((x, y))

sizes = []
for x, y in low_points:
    Q = []
    explored = set()
    explored.add((x, y))
    Q.append((x, y))
    size = 0
    while len(Q) > 0:
        size += 1
        vx, vy = Q.pop(0)
        for nx, ny in get_neighbors(vx, vy):
            if not (nx, ny) in explored and heights[ny][nx] < 9:
                Q.append((nx, ny))
                explored.add((nx, ny))
    sizes.append(size)

sizes.sort(reverse=True)
a, b, c, *_ = sizes
print(a * b * c)