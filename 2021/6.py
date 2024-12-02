def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(6)

from collections import deque

fishes = [0 for _ in range(9)]
for fish in map(int, lines[0].split(',')):
    fishes[fish] += 1

for d in range(0, 256):
    a = fishes.pop(0)
    fishes.append(a)
    fishes[6] += a

print(sum(fishes))
    



# fishes = list(map(int, lines[0].split(',')))
# for d in range(0, 80):
#     for i in range(len(fishes)):
#         if fishes[i] > 0:
#             fishes[i] -= 1
#         else:
#             fishes[i] = 6
#             if i == 0:
#                 n+=1
#             fishes.append(8)
#     print(d, len(fishes))
# print(len(fishes))
# print(n)
