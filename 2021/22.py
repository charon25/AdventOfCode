from os import get_terminal_size
from typing import List, Tuple, Union
import numpy as np


def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(22)

import re
import numpy as np


mx = MX = my = MY = mz = MZ = 0

for line in lines:
    state, *values = re.findall(r'(on|off) x=(-?\d+)\.\.(-?\d+),y=(-?\d+)\.\.(-?\d+),z=(-?\d+)\.\.(-?\d+)', line)[0]
    minx, maxx, miny, maxy, minz, maxz = map(int, values)
    mx = min(mx, minx)
    MX = max(MX, maxx)
    my = min(my, miny)
    MY = max(MY, maxy)
    mz = min(mz, minz)
    MZ = max(MZ, maxz)

space = np.ndarray((MX - mx + 1, MY - my + 1, MZ - mz + 1), dtype=np.int0)

k = 0
for line in lines:
    print(k)
    k += 1
    state, *values = re.findall(r'(on|off) x=(-?\d+)\.\.(-?\d+),y=(-?\d+)\.\.(-?\d+),z=(-?\d+)\.\.(-?\d+)', line)[0]
    minx, maxx, miny, maxy, minz, maxz = map(int, values)
    minx -= mx
    maxx -= mx
    miny -= my
    maxy -= my
    minz -= mz
    maxz -= mz
    space[minx:maxx+1, miny:maxy+1, minz:maxz+1] = 1 if state == 'on' else 0
    
print(np.sum(space))