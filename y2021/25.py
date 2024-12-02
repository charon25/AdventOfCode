from os import get_terminal_size
from typing import List, Tuple, Union
import numpy as np


def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(25)

NY = len(lines)
NX = len(lines[0])

data = [[0 for _ in range(NX)] for _ in range(NY)]
for y, line in enumerate(lines):
    for x, c in enumerate(line):
        if c == 'v':
            data[y][x] = 1
        elif c == '>':
            data[y][x] = -1

moved = 1
k = 0
while moved > 0:
    moved = 0
    new_data = [[0 for _ in range(NX)] for _ in range(NY)]
    # for y in range(NY):
    #     for x in range(NX):
    #         print(['.', 'v', '>'][data[y][x]], end='')
    #     print()
    for c in (-1, 1):
        for y in range(NY):
            for x in range(NX):
                if data[y][x] == c:
                    if c == -1:
                        if x < NX - 1:
                            if data[y][x + 1] == 0:
                                new_data[y][x + 1] = c
                                moved +=1 
                            else:
                                new_data[y][x] = c
                        else:
                            if data[y][0] == 0:
                                new_data[y][0] = c
                                moved +=1 
                            else:
                                new_data[y][x] = c
                    elif c == 1:
                        if y < NY - 1:
                            if data[y + 1][x] != c and new_data[y + 1][x] != -c:
                                new_data[y + 1][x] = c
                                moved +=1 
                            else:
                                new_data[y][x] = c
                        else:
                            if data[0][x] != c and new_data[0][x] != -c:
                                new_data[0][x] = c
                                moved +=1 
                            else:
                                new_data[y][x] = c
    k += 1
    data = new_data

print(k)