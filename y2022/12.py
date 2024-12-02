with open('inputs/12.txt', 'r') as fi:
    lines = fi.read().splitlines()

def can_reach(c1: str, c2: str):
    if c1 == 'S':c1 = 'a'
    if c2 == 'S':c2 = 'a'
    if c1 == 'E':c1 = 'z'
    if c2 == 'E':c2 = 'z'
    return ord(c2) <= ord(c1) + 1

HEIGHT = len(lines)
WIDTH = len(lines[0])    

graph: dict[tuple[int, int], list[tuple[int, int]]] = {}
graph_2: dict[tuple[int, int], list[tuple[int, int]]] = {}

start = end = None

for y, row in enumerate(lines):
    for x, c in enumerate(row):
        graph[(x, y)] = []
        graph_2[(x, y)] = []
        if c == 'S':
            start = (x, y)
        elif c == 'E':
            end = (x, y)

        if y > 0 and can_reach(c, lines[y - 1][x]):
            graph[(x, y)].append((x, y - 1))
        if y < HEIGHT - 1 and can_reach(c, lines[y + 1][x]):
            graph[(x, y)].append((x, y + 1))
        if x > 0 and can_reach(c, lines[y][x - 1]):
            graph[(x, y)].append((x - 1, y))
        if x < WIDTH - 1 and can_reach(c, lines[y][x + 1]):
            graph[(x, y)].append((x + 1, y))

        if y > 0 and can_reach(lines[y - 1][x], c):
            graph_2[(x, y)].append((x, y - 1))
        if y < HEIGHT - 1 and can_reach(lines[y + 1][x], c):
            graph_2[(x, y)].append((x, y + 1))
        if x > 0 and can_reach(lines[y][x - 1], c):
            graph_2[(x, y)].append((x - 1, y))
        if x < WIDTH - 1 and can_reach(lines[y][x + 1], c):
            graph_2[(x, y)].append((x + 1, y))

# Part 1
visited = set((start, ))
Q = [(start, 0)]

while len(Q) > 0:
    u, length = Q.pop(0)
    if u == end:
        break
    
    for v in graph[u]:
        if not v in visited:
            visited.add(v)
            Q.append((v, length + 1))

print(length)

# Part 2
visited = set((end, ))
Q = [(end, 0)]

while len(Q) > 0:
    u, length = Q.pop(0)
    x, y = u
    if lines[y][x] == 'a':
        break
    
    for v in graph_2[u]:
        if not v in visited:
            visited.add(v)
            Q.append((v, length + 1))

print(length)
