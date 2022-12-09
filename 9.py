with open('inputs/9.txt', 'r') as fi:
    lines = fi.read().splitlines()

def is_too_far(hx, hy, tx, ty) -> bool:
    return abs(hx - tx) > 1 or abs(hy - ty) > 1

def sign(x):
    if x == 0:
        return 0
    return abs(x) // x

def move(hx, hy, direction):
    if direction == 'U':
        return (hx, hy - 1)
    if direction == 'D':
        return (hx, hy + 1)
    if direction == 'L':
        return (hx - 1, hy)

    return (hx + 1, hy) # right

def tail_move(hx, hy, tx, ty):
    if not is_too_far(hx, hy, tx, ty):
        return (tx, ty)

    return (tx + sign(hx - tx), ty + sign(hy - ty))

# Part 1
hx = hy = tx = ty = 0

visited_by_tail = {(tx, ty)}

for line in lines:
    direction, count = line.split()
    count = int(count)
    for _ in range(count):
        hx, hy = move(hx, hy, direction)
        tx, ty = tail_move(hx, hy, tx, ty)
        visited_by_tail.add((tx, ty))

print(len(visited_by_tail))

#Part 2
N = 10

rope = [(0, 0) for _ in range(N)]

visited_by_tail = {rope[-1]}

for line in lines:
    direction, count = line.split()
    count = int(count)
    for _ in range(count):
        rope[0] = move(*rope[0], direction)
        for i in range(1, N):
            rope[i] = tail_move(*rope[i - 1], *rope[i])
        visited_by_tail.add(rope[-1])


print(len(visited_by_tail))
