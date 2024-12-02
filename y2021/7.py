def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(7)

crabs = list(map(int, lines[0].split(',')))
M = max(crabs)
import numpy as np
crabs = np.array(crabs, dtype=np.float64)

def fuel(d):
    return (d * (d+1)) // 2

best_f = 10**10
best_a = None
for a in range(0, 2*M+1):
    # f = np.sum(np.abs(crabs - a))
    f = np.sum(fuel(np.abs(crabs - a)))
    if f < best_f:
        best_f = f
        best_a = a
print(best_a, '; fuel :', best_f)