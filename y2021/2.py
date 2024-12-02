def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(2)
x = 0
d = 0
# for line in lines:
#     if 'forward' in line:
#         x += int(line.split(' ')[1])
#     elif 'up' in line:
#         d -= int(line.split(' ')[1])
#     elif 'down' in line:
#         d += int(line.split(' ')[1])
a = 0
for line in lines:
    if 'forward' in line:
        x += int(line.split(' ')[1])
        d += a * int(line.split(' ')[1])
    elif 'up' in line:
        a -= int(line.split(' ')[1])
    elif 'down' in line:
        a += int(line.split(' ')[1])

print(x*d)