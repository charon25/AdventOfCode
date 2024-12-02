def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

# lines = get_input(16)
min_x = 119
max_x = 176
min_y = -141
max_y = -84
# min_x = 20
# max_x = 30
# min_y = -10
# max_y = -5

import math


# for u0 in range(min_y - 2, 1000)

lu = []
# u0 = 1
for u0 in range(min_y - 2, 10000):
    if u0 > 0:
        h = (u0 * (u0 + 1)) // 2
        n = 1
        n = math.ceil((-1 + math.sqrt(1 + 8 * (h - max_y))) / 2)
        if h - (n * (n + 1)) // 2 >= min_y:
            lu.append(u0)
    elif u0 <= 0:
        y = 0
        uu = u0
        b = False
        while True:
            y += uu
            uu -= 1
            if (min_y <= y <= max_y):
                b = True
            if y < min_y:
                break
        if b:
            lu.append(u0)


lv = []
for v in range(10000):
    x = 0
    vv = v
    b = False
    while vv > 0:
        x += vv
        vv -= 1
        if (min_x <= x <= max_x):
            b = True
        if x > max_x:
            break
    if b:
        lv.append(v)

good = []
for v0 in lv:
    for u0 in lu:
        x = y = 0
        v = v0
        u = u0
        g = False
        while True:
            x += v
            y += u
            if v > 0:
                v -= 1
            u -= 1
            if min_x <= x <= max_x and min_y <= y <= max_y:
                g = True
            if x > max_x or y < min_y:
                break
        if g:
            good.append((v0, u0))

# print(good)
# print('\n'.join(f'{v},{u}' for v, u in good))
print(len(good))

# print(lv)
# print(lu)
# print()
# print(len(lu) * len(lv))