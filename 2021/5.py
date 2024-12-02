def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(5)

dic = {}
import re
for line in lines:
    if line == '':continue
    x1, y1, x2, y2 = list(map(int, re.findall(r'(\d+),(\d+) -> (\d+),(\d+)', line)[0]))
    dx = 0 if x1 == x2 else (-1 if x1 > x2 else 1)
    dy = 0 if y1 == y2 else (-1 if y1 > y2 else 1)
    L = max(x2 - x1, y2 - y1, x1 - x2, y1 - y2)
    for k in range(0, L + 1):
        coords = (x1 + k * dx, y1 + k * dy)
        dic[coords] = 1 if not coords in dic else dic[coords] + 1
    # if x1 != x2 and y1 != y2:continue
    # if x1 == x2:
    #     if y1 == y2:
    #         dic[(x1, y1)] = 1 if not (x1, y1) in dic else dic[(x1, y1)] + 1
    #     else:
    #         if y2 < y1:
    #             y1, y2 = y2, y1
    #         for y in range(y1, y2 + 1):
    #             dic[(x1, y)] = 1 if not (x1, y) in dic else dic[(x1, y)] + 1
    # else:
    #     if x2 < x1:
    #         x1, x2 = x2, x1
    #     for x in range(x1, x2 + 1):
    #         dic[(x, y1)] = 1 if not (x, y1) in dic else dic[(x, y1)] + 1

res = sum(value >= 2 for _, value in dic.items())
print(res)