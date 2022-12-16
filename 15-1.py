import re

with open('inputs/15.txt', 'r') as fi:
    lines = fi.read().splitlines()

TARGET = 2_000_000

min_x = 10**10
max_x = -10**10

sensors: list[tuple[int, int, int]] = list()
sensors_set: set[tuple[int, int]] = set()
beacons_set: set[tuple[int, int]] = set()

for line in lines:
    sx, sy, bx, by = map(int, re.findall(r'Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)', line)[0])
    d = abs(sx - bx) + abs(sy - by)

    if sy - d > TARGET or sy + d < TARGET:
        continue

    dty = abs(TARGET - sy)
    dx = d - dty
    min_x = min(min_x, sx - dx)
    max_x = max(max_x, sx + dx)

    sensors.append((sx, sy, d, dx))
    sensors_set.add((sx, sy))
    beacons_set.add((bx, by))


total_impossible = 0

x = min_x
while x <= max_x:
    if (x, TARGET) in beacons_set:
        continue

    for sx, sy, d, dx in sensors:
        if abs(x - sx) + abs(TARGET - sy) <= d:
            total_impossible += (sx + dx + 1 - x)
            x = sx + dx + 1
            break
    else:
        x += 1

if x > max_x:
    total_impossible -= (x - max_x)

print(total_impossible)
