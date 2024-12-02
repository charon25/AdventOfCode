from typing import final


def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(12)

caves = {}

for line in lines:
    cave1, cave2 = line.split('-')
    if not cave1 in caves:caves[cave1] = [cave2]
    else:caves[cave1].append(cave2)

    if not cave2 in caves:caves[cave2] = [cave1]
    else:caves[cave2].append(cave1)

# Q = [['start']]
# final_paths = []

# while len(Q) > 0:
#     path = Q.pop(0)
#     v = path[-1]
#     if v == 'end':
#         final_paths.append(path)
#         continue

#     for neighbor in caves[v]:
#         if neighbor.isupper() or not neighbor in path:
#             Q.append(path + [neighbor])

# print(len(final_paths))

def can_go_through(path, cave):
    if not cave in path:
        return True
    if cave == 'start' or cave == 'end':
        return False
    for c in path:
        if not c.islower():continue
        if path.count(c) > 1:return False
    return True

Q = [['start']]
final_paths = []

while len(Q) > 0:
    path = Q.pop(0)
    v = path[-1]
    if v == 'end':
        final_paths.append(path)
        continue

    for neighbor in caves[v]:
        if not neighbor.islower() or can_go_through(path, neighbor):
            Q.append(path + [neighbor])

print(len(final_paths))