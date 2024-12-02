from typing import final


def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(13)

dots = []
folds = []

size_x = size_y = 0

for line in lines:
    if line == '':continue
    if line[0].isnumeric():
        x, y = map(int, line.split(','))
        size_x = max(size_x, x)
        size_y = max(size_y, y)
        dots.append((x, y))
    elif line.startswith('fold along'):
        line = line[11:]
        d, val = line.split('=')
        folds.append((d, int(val)))

print(size_x, size_y)

def print_paper(dots):
    # return
    global size_x, size_y
    for y in range(size_y + 1):
        for x in range(size_x + 1):
            if (x, y) in dots:
                print('#', end='')
            else:
                print('.', end='')
        print()

# print_paper(dots)
for d, val in folds:
    new_dots = []
    print(size_x, size_y, d, val*2)
    if d == 'x':
        for x, y in dots:
            p = (x if x < val else 2*val - x, y)
            if not p in new_dots:
                new_dots.append(p)
        size_x = (size_x // 2) + 1
    elif d == 'y':
        for x, y in dots:
            p = (x, y if y < val else 2*val - y)
            if not p in new_dots:
                new_dots.append(p)
        size_y = (size_y // 2) + 1
    dots = new_dots
    if size_x < 100:print_paper(dots)
    # break
print('='*20)
print_paper(dots)
print(len(dots))