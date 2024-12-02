from os import get_terminal_size
from typing import List, Tuple, Union
import numpy as np


def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

class Game:
    def __init__(self, p1, p2, s1, s2, t) -> None:
        self.p1 = p1 % 10
        self.p2 = p2 % 10
        self.s1 = s1
        self.s2 = s2
        self.t = t
        self.children = []
        if self.s1 < 21 and self.s2 < 21:
            self.play()
    
    def play(self):
        if self.t % 6 < 3:
            add = (self.p1 if self.p1 > 0 else 10) if self.t % 6 == 2 else 0
            g1 = Game(self.p1 + 1, self.p2, self.s1 + add, self.s2, self.t + 1)
            g2 = Game(self.p1 + 2, self.p2, self.s1 + add, self.s2, self.t + 1)
            g3 = Game(self.p1 + 3, self.p2, self.s1 + add, self.s2, self.t + 1)
            self.children.extend([g1, g2, g3])
        else:
            add = (self.p2 if self.p2 > 0 else 10) if self.t % 6 == 5 else 0
            g1 = Game(self.p1, self.p2 + 1, self.s1, self.s2 + add, self.t + 1)
            g2 = Game(self.p1, self.p2 + 2, self.s1, self.s2 + add, self.t + 1)
            g3 = Game(self.p1, self.p2 + 3, self.s1, self.s2 + add, self.t + 1)
            self.children.extend([g1, g2, g3])

p1 = 7
p2 = 10
p1 = 4
p2 = 8
s1 = 0
s2 = 0

g = Game(p1, p2, s1, s2, 0)

# t = 1
# n = 0

# nd = 0

# for d in list(range(1, 101)) * 100:
#     if t == 1:
#         p1 = (p1 + d) % 10
#     elif t == 2:
#         p2 = (p2 + d) % 10
#     n += 1
#     nd += 1
#     # print(p1, p2, s1, s2, n, t, d)
#     if n == 3:
#         if t == 1:
#             s1 += (p1 if p1 > 0 else 10)
#         elif t == 2:
#             s2 += (p2 if p2 > 0 else 10)
#         t = 3 - t
#         if s1 >= 1000 or s2 >= 1000:
#             break
#         n = 0

# print(min(s1, s2) * nd)
