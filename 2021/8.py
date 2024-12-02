def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(8)

# N = 0
# for line in lines:
#     line = line.split('|')[1].split()
#     N += sum(len(digit) in (2, 3, 4, 7) for digit in line)
# print(N)

import itertools
PERMS = list(itertools.permutations([_ for _ in range(7)]))

DIGITS = (
    [0, 1, 2, 4, 5, 6],
    [2, 5],
    [0, 2, 3, 4, 6],
    [0, 2, 3, 5, 6],
    [1, 2, 3, 5],
    [0, 1, 3, 5, 6],
    [0, 1, 3, 4, 5, 6],
    [0, 2, 5],
    [0, 1, 2, 3, 4, 5, 6],
    [0, 1, 2, 3, 5, 6]
)

def asc(c):
    return ord(c) - 97

def signal_to_digit(signal, perm):
    signal = list(map(asc, signal))
    for i, c in enumerate(signal):
        signal[i] = perm[c]
    signal.sort()
    return signal

S = 0
for line in lines:
    signals, outputs = line.split('|')
    signals = signals.split()
    outputs = outputs.split()
    for perm in PERMS:
        if all(signal_to_digit(signal, perm) in DIGITS for signal in signals):
            n = int(''.join(str(DIGITS.index(signal_to_digit(output, perm))) for output in outputs))
            S += n
            break
print(S)