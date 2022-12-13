with open('../inputs/03.txt', 'r') as fi:
    lines = fi.read().splitlines()

def get_priority(c: str):
    if ord(c) <= ord('Z'):
        return ord(c) - ord('A') + 27
    return ord(c) - ord('a') + 1

# Part 1
total_priority = 0
for line in lines:
    length = len(line)
    wrong_item = (set(line[:length//2]) & set(line[length//2:])).pop()
    total_priority += get_priority(wrong_item)

print(total_priority)

# Part 2
total_priority_2 = 0
for i in range(0, len(lines), 3):
    line1, line2, line3 = lines[i], lines[i + 1], lines[i + 2]
    badge = (set(line1) & set(line2) & set(line3)).pop()
    total_priority_2 += get_priority(badge)

print(total_priority_2)
