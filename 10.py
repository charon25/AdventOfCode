with open('inputs/10.txt', 'r') as fi:
    lines = fi.read().splitlines()

# Part 1
X = 1

total_signal_strength = 0
cycle = 0
counted = False

for k, line in enumerate(lines):
    if cycle % 40 == 20 and not counted:
        total_signal_strength += X * cycle
    counted = False
    if line.startswith('noop'):
        cycle += 1
    elif line.startswith('addx'):
        value = int(line.split()[1])
        if (cycle + 1) % 40 == 20:
            total_signal_strength += X * (cycle + 1)
        elif (cycle + 2) % 40 == 20:
            total_signal_strength += X * (cycle + 2)
            counted = True
        X += value
        cycle += 2

print(total_signal_strength)

# Part 2
X = 1

cycle = 0
screen = [[0 for _ in range(40)] for __ in range(6)]

def draw(cycle, screen, X):
    cycle -= 1
    if abs(cycle % 40 - X) <= 1:
        screen[cycle // 40][cycle % 40] = 1

for k, line in enumerate(lines):
    counted = False
    if line.startswith('noop'):
        cycle += 1
        draw(cycle, screen, X)
    elif line.startswith('addx'):
        value = int(line.split()[1])
        cycle += 1
        draw(cycle, screen, X)
        cycle += 1
        draw(cycle, screen, X)
        X += value

print(*[''.join([('.', '#')[cell] for cell in row]) for row in screen], sep="\n")
