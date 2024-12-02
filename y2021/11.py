def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(11)

class Octopus:
    def __init__(self, p, n) -> None:
        self.p = p
        self.n = n
        self.flashed = False
        self.neighbors = []
    
    def add_neighbor(self, neighbor):
        self.neighbors.append(neighbor)
    
    def increment(self, flash=False):
        self.p += 1
        if flash:
            self.flash()
    
    def flash(self):
        # print(self.n, self.p > 9 and not self.flashed)
        if self.p > 9 and not self.flashed:
            self.flashed = True
            for neighbor in self.neighbors:
                neighbor.increment(flash=True)
    
    def reset(self):
        self.flashed = False
        if self.p > 9:
            self.p = 0
    
    def __str__(self) -> str:
        return 'Power : {p}'

octopuses = []
octo = {}

for y, line in enumerate(lines):
    for x, c in enumerate(line):
        octopus = Octopus(int(c), f'{x}/{y}')
        octopuses.append(octopus)
        octo[(x, y)] = octopus

def get_neighbors(x, y):
    neighbors = []
    for dx in (-1, 0, 1):
        for dy in (-1, 0, 1):
            if dx == dy == 0:continue
            if 0 <= x + dx < 10 and 0 <= y + dy < 10:
                neighbors.append((x + dx, y + dy))
    return neighbors 

for y, line in enumerate(lines):
    for x, c in enumerate(line):
        for nx, ny in get_neighbors(x, y):
            octo[(x, y)].add_neighbor(octo[(nx, ny)])

def print_octo():
    global octopuses
    for i, octopus in enumerate(octopuses):
        print(octopus.p, end='')
        if i % 10 == 9:
            print()

# flashes = 0
# for d in range(100):
d = 0
while True:
    for octopus in octopuses:octopus.increment()
    for octopus in octopuses:octopus.flash()
    # flashes += sum(octopus.flashed for octopus in octopuses)
    for octopus in octopuses:octopus.reset()
    d += 1
    if sum(octopus.p for octopus in octopuses) == 0:
        break

# print(flashes)
print(d)