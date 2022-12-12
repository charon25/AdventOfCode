with open('inputs/04.txt', 'r') as fi:
    lines = fi.read().splitlines()


count_fully_contained = 0
count_overlap = 0
for line in lines:
    elf1, elf2 = line.split(',')
    start1, end1 = map(int, elf1.split('-'))
    start2, end2 = map(int, elf2.split('-'))
    
    if (start1 >= start2 and end1 <= end2) or (start1 <= start2 and end1 >= end2):
        count_fully_contained += 1
    
    if (start1 <= start2 <= end1) or (start1 <= end2 <= end1) or (start2 <= start1 <= end2) or (start2 <= end1 <= end2):
        count_overlap += 1

# Part 1
print(count_fully_contained)

# Part 2
print(count_overlap)
