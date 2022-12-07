import re

with open('inputs/5.txt', 'r') as fi:
    lines = fi.read().splitlines()

stacks_count = (len(lines[0]) + 1) // 4
stacks = [[] for _ in range(stacks_count)]

# Reading of the stacks until the line starts with " 1"
for line in lines:
    if line.startswith(' 1'):
        break
    crates = re.findall(r'    |\[(\w)\]', line)
    for i, crate in enumerate(crates):
        if crate:
            stacks[i].insert(0, crate)

stacks_2 = [list(stack) for stack in stacks]

moves = re.findall(r'move (\d+) from (\d+) to (\d+)', ''.join(lines))
for move in moves:
    quantity, start, end = map(int, move)
    # 1-indexing to 0-indexing
    start -= 1
    end -= 1

    # Remove the last n crate from start and add them to end in reverse order
    stacks[end].extend(stacks[start][-quantity:][::-1])
    stacks[start] = stacks[start][:-quantity]

    # Remove the last n crate from start and add them to end in same order
    stacks_2[end].extend(stacks_2[start][-quantity:])
    stacks_2[start] = stacks_2[start][:-quantity]

# Part 1
print(''.join(stack[-1] for stack in stacks))

# Part 2
print(''.join(stack[-1] for stack in stacks_2))
