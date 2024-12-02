def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(3)
S = len(lines[0])
M = (2**S) - 1
# gamma = [[0, 0] for _ in range(S)]
# for line in lines:
#     for i in range(S):
#         gamma[i][int(line[i])] += 1

# G = ''
# for i in range(S):
#     if gamma[i][0] == gamma[i][1]:
#         G += '1'
#     elif gamma[i][0] > gamma[i][1]:
#         G += '0'
#     else:
#         G += '1'

def get_most_common(l, k):
    N = [0, 0]
    for ele in l:
        N[int(ele[k])] += 1
    return str(int(N[0] <= N[1]))

oxy = [line for line in lines]
co2 = [line for line in lines]
k = 0
while len(oxy) > 1:
    most_common = get_most_common(oxy, k)
    new_oxy = []
    for line in oxy:
        if line[k] == most_common:
            new_oxy.append(line)
    oxy = new_oxy.copy()
    k += 1

k = 0
while len(co2) > 1:
    most_common = get_most_common(co2, k)
    new_co2 = []
    for line in co2:
        if line[k] != most_common:
            new_co2.append(line)
    co2 = new_co2.copy()
    k += 1

oxy = int(oxy[0], 2)
co2 = int(co2[0], 2)
print(oxy * co2)

# G = int(G, 2)
# print(G * (M - G))
