from os import get_terminal_size
from typing import List, Tuple, Union
import numpy as np


def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(18)

def moit_up(n):
    return -(n // -2)

class Int:
    def __init__(self, value: int) -> None:
        self.value = value
    
    def get_magnitude(self) -> int:
        return self.value

    def __str__(self) -> str:
        return f'{self.value}'
    def __repr__(self) -> str:
        return str(self)
import copy
class Number:
    def __init__(self, left: Union[Int, 'Number'], right: Union[Int, 'Number']) -> None:
        self.left = left
        self.right = right

    def get_magnitude(self) -> int:
        return 3 * self.left.get_magnitude() + 2 * self.right.get_magnitude()

    def __str__(self) -> str:
        return f'[{self.left}, {self.right}]'
    def __repr__(self) -> str:
        return str(self)

    def add(self, number: Union[Int, 'Number']) -> 'Number':
        return Number(copy.deepcopy(self), copy.deepcopy(number))

    @staticmethod
    def get_leaves(root: 'Number', coord: str = '', depth: int = 0) -> List[Tuple[Union[Int, 'Number'], str, int]]:
        leaves: List[Tuple[Union[Int, 'Number'], str, int]] = []
        if type(root.left) == Int and type(root.right) == Int:
            leaves.append((root, coord, depth))

        if type(root.left) == Int:
            leaves.append((root.left, coord + '1', depth + 1))
        else:
            leaves.extend(Number.get_leaves(root.left, coord + '1', depth + 1))

        if type(root.right) == Int:
            leaves.append((root.right, coord + '2', depth + 1))
        else:
            leaves.extend(Number.get_leaves(root.right, coord + '2', depth + 1))
        
        return leaves
    
    def get(self, root: Union[Int, 'Number'] = None, coord: str = '', depth: int = -1) -> Union[Int, 'Number']:
        if root is None:
            root = self
        if depth == -1:
            depth = len(coord)
        elif depth == 0:
            return root
        c = coord[-depth]
        if type(root) == Int:
            return None
        if c == '1':
            return self.get(root.left, coord, depth - 1)
        elif c == '2':
            return self.get(root.right, coord, depth - 1)
        return None

    def set(self, value: Union[Int, 'Number'], root: Union[Int, 'Number'] = None, coord: str = '') -> Union[Int, 'Number']:
        if root is None:
            root = self
        
        for depth, c in enumerate(coord):
            if c == '1':
                root = root.left
            else:
                root = root.right
        


        # elif depth == 0:
        #     return root
        # c = coord[-depth]
        # if type(root) == Int:
        #     return None
        # if c == '1':
        #     return self.get(root.left, coord, depth - 1)
        # elif c == '2':
        #     return self.get(root.right, coord, depth - 1)
        # return None


    def get_right_neighbor(self, coord: str):
        last_one = coord.rfind('1')
        if last_one == -1:
            return None
        coord = coord[:last_one] + '2'
        previous = None
        while True:
            number = self.get(coord=coord)
            if number is None:
                return previous
            previous = number
            coord += '1'

    def get_left_neighbor(self, coord: str):
        last_two = coord.rfind('2')
        if last_two == -1:
            return None
        coord = coord[:last_two] + '1'
        previous = None
        while True:
            number = self.get(coord=coord)
            if number is None:
                return previous
            previous = number
            coord += '2'
    
    def is_int_pair(self):
        return type(self.left) == Int and type(self.right) == Int

    def has_explodes(self):
        for number, coord, depth in Number.get_leaves(self):
            if type(number) == Number and depth >= 4 and number.is_int_pair():
                return True
        return False

    def reduce(self) -> None:
        # for n, c, d in Number.get_leaves(self):
        #     # #print(n)
        #     #print(n, d, c, n.is_int_pair() if type(n) == Number else '-')

        while True:
            did_op = False
            for number, coord, depth in Number.get_leaves(self):
                if type(number) == Number and depth >= 4 and number.is_int_pair():
                    #print('='*10 + '\n', 'explode', number)
                    left_neighbor = self.get_left_neighbor(coord)
                    right_neighbor = self.get_right_neighbor(coord)
                    #print(self)
                    if left_neighbor is not None:
                        left_neighbor.value += number.left.value
                    if right_neighbor is not None:
                        right_neighbor.value += number.right.value
                    exec('self' + coord.replace('1', '.left').replace('2', '.right') + ' = Int(0)')
                    did_op = True
            
            for number, coord, depth in Number.get_leaves(self):
                if type(number) == Int and number.value >= 10:
                    #print('='*10 + '\n', 'split', number)
                    left = number.value // 2
                    right = moit_up(number.value)
                    exec('self' + coord.replace('1', '.left').replace('2', '.right') + f' = Number(Int({left}), Int({right}))')
                    did_op = True
                    break
                    # if self.has_explodes():
                    #     break
            if not did_op:
                break

    @staticmethod
    def create_from_string(string: str) -> Union['Number', Int]:
        depth = 0
        for i, c in enumerate(string):
            if c == '[':depth += 1
            if c == ']':depth -= 1
            if c == ',' and depth == 1:
                break
        left = string[1:i]
        right = string[i+1:-1]
        if left.isnumeric():
            left = Int(int(left))
        else:
            left = Number.create_from_string(left)
        if right.isnumeric():
            right = Int(int(right))
        else:
            right = Number.create_from_string(right)
        return Number(left, right)


numbers: List[Number] = []
for line in lines:
    n = Number.create_from_string(line)
    numbers.append(n)

# a = numbers[0]
# #print(a)
# for b in numbers[1:]:
#     a = a.add(b)
#     print(a)
#     a.reduce()
#     print('')
#     print(a)

# print()

# print(a.get_magnitude())

# import itertools

max_magnitude = -1
for i, n1 in enumerate(numbers):
    for j, n2 in enumerate(numbers):
        if i == j:
            continue
        n = n1.add(n2)
        n.reduce()
        magn = n.get_magnitude()
        if magn > max_magnitude:
            print(n1)
            print(n2)
            print(magn)
            print('='*30)
            max_magnitude = magn
print(max_magnitude)


