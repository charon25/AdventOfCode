with open('inputs/1.txt', 'r') as fi:
    lines = fi.read().splitlines()

calories = [0]
for line in lines:
    if line == '':
        calories.append(0)
    else:
        calories[-1] += int(line)

# Part 1
print(max(calories))

# Part 2
print(sum(sorted(calories, reverse=True)[:3]))
