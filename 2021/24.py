from os import get_terminal_size
from typing import List, Tuple, Union
import numpy as np


def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(24)

ops = []
k = 14
for line in lines:
    if len(line.split()) == 3:
        op, a, b = line.split()
    else:
        op, a = line.split()
    if op == 'inp':
        ops.append(f'{a} = input[{k}]')
        k -= 1
    elif op == 'add':
        ops.append(f'{a} = {a} + {b}')
    elif op == 'mul':
        ops.append(f'{a} = {a} * {b}')
    elif op == 'div':
        ops.append(f'{a} = {a} / {b}')
    elif op == 'mod':
        ops.append(f'{a} = {a} % {b}')
    elif op == 'div':
        ops.append(f'{a} = 1 if {a} == {b} else 0')

print('\n'.join(ops))