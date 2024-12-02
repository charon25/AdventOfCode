codes = []
for d in range(25):
    codes.append(f"""
for y in range({104 + 4*d}):
    data{2 + 2*d}[y][0] = 1
    data{2 + 2*d}[y][-1] = 1

for x in range({104 + 4*d}):
    data{2 + 2*d}[0][x] = 1
    data{2 + 2*d}[-1][x] = 1

for y in range({104 + 4*d}):
    data{2 + 2*d}[y].insert(0, 1)
    data{2 + 2*d}[y].append(1)

data{2 + 2*d}.insert(0, [1 for _ in range({106 + 4*d})])
data{2 + 2*d}.append([1 for _ in range({106 + 4*d})])

def get_bin{2 + 2*d}(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data{2 + 2*d}[y+dy][x+dx])
    return int(b, 2)

data{3 + 2*d} = [[0 for _ in range({106 + 4*d})] for _ in range({106 + 4*d})]

for y in range(1, {105 + 4*d}):
    for x in range(1, {105 + 4*d}):
        data{3 + 2*d}[y][x] = int(code[get_bin{2 + 2*d}(x, y)])

for y in range({106 + 4*d}):
    data{3 + 2*d}[y].insert(0, 0)
    data{3 + 2*d}[y].append(0)

data{3 + 2*d}.insert(0, [0 for _ in range({108 + 4*d})])
data{3 + 2*d}.append([0 for _ in range({108 + 4*d})])

def get_bin{3 + 2*d}(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data{3 + 2*d}[y+dy][x+dx])
    return int(b, 2)

data{4 + 2*d} = [[0 for _ in range({108 + 4*d})] for _ in range({108 + 4*d})]

for y in range(1, {107 + 4*d}):
    for x in range(1, {107 + 4*d}):
        data{4 + 2*d}[y][x] = int(code[get_bin{3 + 2*d}(x, y)])
""")
print(len(codes))
with open('20ter.py', 'w') as fo:
    fo.writelines('\n\n'.join(codes))