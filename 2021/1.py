import requests

def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(1)
lines = list(map(int, lines))
sums = [lines[i]+lines[i+1]+lines[i+2] for i in range(len(lines) - 2)]
c = 0
for i, s in enumerate(sums[1:], start=1):
    if s > sums[i-1]:
        c += 1

print(c)