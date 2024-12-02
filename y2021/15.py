def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(15)

len_y = len(lines)
len_x = len(lines[0])
LEN_Y = len(lines) * 5
LEN_X = len(lines[0]) * 5


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

data = [[0 for _ in range(LEN_X)] for _ in range(LEN_Y)]

for y, line in enumerate(lines):
    for x, cell in enumerate(line):
        for mx in range(5):
            for my in range(5):
                value = int(lines[y][x]) + mx + my
                while value > 9:
                    value -= 9
                data[y + my * len_y][x + mx * len_x] = value

from queue import PriorityQueue

lengths = {}
dist = {}
prev = {}
visited = {}
Q = PriorityQueue()

for y in range(LEN_Y):
    for x in range(LEN_X):
        lengths[(x, y)] = []
        for nx, ny in get_neighbors(x, y):
            lengths[(x, y)].append(((nx, ny), data[ny][nx]))
        dist[(x, y)] = 10**12
        prev[(x, y)] = None
        visited[(x, y)] = False
        Q.put((0 if x == y == 0 else 10**12, (x, y)))

dist[(0, 0)] = 0

def get_min_distance():
    global Q, dist
    min_dist = 10**13
    min_u = None
    for u in Q:
        if dist[u] < min_dist:
            min_dist = dist[u]
            min_u = u
    return min_u

min_d = 0
min_u = (0, 0)

while 1:
    while 1:
        d, u = Q.get()
        if not visited[u]:break

    # print(u)
    # input()

    if u == (LEN_X - 1, LEN_Y - 1):
        break

    for v, length in lengths[u]:
        if not visited[v]:
            alt = dist[u] + length
            if alt < dist[v]:
                dist[v] = alt
                prev[v] = u
                Q.put((alt, v))

path = []
u = (LEN_X - 1, LEN_Y - 1)
while u is not None:
    path.insert(0, u)
    u = prev[u]

total_risk = 0
for x, y in path[1:]:
    total_risk += data[y][x]

print(total_risk)
