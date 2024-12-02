from typing import final


def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(14)

polymer = list(lines[0])
rules = {}

for line in lines[2:]:
    pair, new = line.split(' -> ')
    rules[pair] = new


# for d in range(40):
#     new_polymer = []
#     for k in range(len(polymer) - 1):
#         a, b = polymer[k], polymer[k+1]
#         new_polymer.append(a)
#         pair = f'{a}{b}'
#         if pair in rules:
#             new_polymer.append(rules[pair])
#         if k == len(polymer) - 2:
#             new_polymer.append(b)
#     polymer = new_polymer
#     print(d)

# letters = set(polymer)
# occ = []
# for l in letters:
#     occ.append((polymer.count(l), l))
# occ.sort(reverse=True)

# print(occ[0][0] - occ[-1][0])

LETTERS = 'BCFHKNOPSV'

first_last = {l: int(polymer[0] == l) + int(polymer[-1] == l) for l in LETTERS}

def get_empty_pairs():
    pairs = {}
    for l in LETTERS:
        pairs[l] = {}
        for l2 in LETTERS:
            pairs[l][l2] = 0
    return pairs

pairs = get_empty_pairs()

for k in range(len(polymer) - 1):
    l1, l2 = polymer[k:k+2]
    pairs[l1][l2] += 1

for d in range(40):
    new_pairs = get_empty_pairs()
    for l1 in LETTERS:
        for l2 in LETTERS:
            if pairs[l1][l2] == 0:continue
            pair = l1 + l2
            li = rules[pair]
            new_pairs[l1][li] += pairs[l1][l2]
            new_pairs[li][l2] += pairs[l1][l2]
    pairs = new_pairs

totals = {l: 0 for l in LETTERS}
for l1 in LETTERS:
    for l2 in LETTERS:
        totals[l2] += pairs[l1][l2]
        totals[l1] += pairs[l1][l2]

occ = []
for l in LETTERS:
    occ.append(((totals[l] + first_last[l]) // 2, l))

occ.sort(reverse=True)

print(occ[0][0] - occ[-1][0])
