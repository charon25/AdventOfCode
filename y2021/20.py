from os import get_terminal_size
from typing import List, Tuple, Union
import numpy as np


def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(20)

code = lines[0].replace('.', '0').replace('#', '1')

data = [[0 for _ in range(100)] for _ in range(100)]

for y, line in enumerate(lines[2:]):
    for x, c in enumerate(line):
        if c == '#':
            data[y][x] = 1

for y in range(100):
    data[y].insert(0, 0)
    data[y].insert(0, 0)
    data[y].append(0)
    data[y].append(0)

data.insert(0, [0 for _ in range(104)])
data.insert(0, [0 for _ in range(104)])
data.append([0 for _ in range(104)])
data.append([0 for _ in range(104)])

def get_bin(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data[y+dy][x+dx])
    return int(b, 2)

data2 = [[0 for _ in range(104)] for _ in range(104)]

for y in range(1, 103):
    for x in range(1, 103):
        data2[y][x] = int(code[get_bin(x, y)])
# ICI

for y in range(104):
    data2[y][0] = 1
    data2[y][-1] = 1

for x in range(104):
    data2[0][x] = 1
    data2[-1][x] = 1

for y in range(104):
    data2[y].insert(0, 1)
    data2[y].append(1)

data2.insert(0, [1 for _ in range(106)])
data2.append([1 for _ in range(106)])

def get_bin2(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data2[y+dy][x+dx])
    return int(b, 2)

data3 = [[0 for _ in range(106)] for _ in range(106)]

for y in range(1, 105):
    for x in range(1, 105):
        data3[y][x] = int(code[get_bin2(x, y)])

for y in range(106):
    data3[y].insert(0, 0)
    data3[y].append(0)

data3.insert(0, [0 for _ in range(108)])
data3.append([0 for _ in range(108)])

def get_bin3(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data3[y+dy][x+dx])
    return int(b, 2)

data4 = [[0 for _ in range(108)] for _ in range(108)]

for y in range(1, 107):
    for x in range(1, 107):
        data4[y][x] = int(code[get_bin3(x, y)])



for y in range(108):
    data4[y][0] = 1
    data4[y][-1] = 1

for x in range(108):
    data4[0][x] = 1
    data4[-1][x] = 1

for y in range(108):
    data4[y].insert(0, 1)
    data4[y].append(1)

data4.insert(0, [1 for _ in range(110)])
data4.append([1 for _ in range(110)])

def get_bin4(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data4[y+dy][x+dx])
    return int(b, 2)

data5 = [[0 for _ in range(110)] for _ in range(110)]

for y in range(1, 109):
    for x in range(1, 109):
        data5[y][x] = int(code[get_bin4(x, y)])

for y in range(110):
    data5[y].insert(0, 0)
    data5[y].append(0)

data5.insert(0, [0 for _ in range(112)])
data5.append([0 for _ in range(112)])

def get_bin5(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data5[y+dy][x+dx])
    return int(b, 2)

data6 = [[0 for _ in range(112)] for _ in range(112)]

for y in range(1, 111):
    for x in range(1, 111):
        data6[y][x] = int(code[get_bin5(x, y)])



for y in range(112):
    data6[y][0] = 1
    data6[y][-1] = 1

for x in range(112):
    data6[0][x] = 1
    data6[-1][x] = 1

for y in range(112):
    data6[y].insert(0, 1)
    data6[y].append(1)

data6.insert(0, [1 for _ in range(114)])
data6.append([1 for _ in range(114)])

def get_bin6(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data6[y+dy][x+dx])
    return int(b, 2)

data7 = [[0 for _ in range(114)] for _ in range(114)]

for y in range(1, 113):
    for x in range(1, 113):
        data7[y][x] = int(code[get_bin6(x, y)])

for y in range(114):
    data7[y].insert(0, 0)
    data7[y].append(0)

data7.insert(0, [0 for _ in range(116)])
data7.append([0 for _ in range(116)])

def get_bin7(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data7[y+dy][x+dx])
    return int(b, 2)

data8 = [[0 for _ in range(116)] for _ in range(116)]

for y in range(1, 115):
    for x in range(1, 115):
        data8[y][x] = int(code[get_bin7(x, y)])



for y in range(116):
    data8[y][0] = 1
    data8[y][-1] = 1

for x in range(116):
    data8[0][x] = 1
    data8[-1][x] = 1

for y in range(116):
    data8[y].insert(0, 1)
    data8[y].append(1)

data8.insert(0, [1 for _ in range(118)])
data8.append([1 for _ in range(118)])

def get_bin8(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data8[y+dy][x+dx])
    return int(b, 2)

data9 = [[0 for _ in range(118)] for _ in range(118)]

for y in range(1, 117):
    for x in range(1, 117):
        data9[y][x] = int(code[get_bin8(x, y)])

for y in range(118):
    data9[y].insert(0, 0)
    data9[y].append(0)

data9.insert(0, [0 for _ in range(120)])
data9.append([0 for _ in range(120)])

def get_bin9(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data9[y+dy][x+dx])
    return int(b, 2)

data10 = [[0 for _ in range(120)] for _ in range(120)]

for y in range(1, 119):
    for x in range(1, 119):
        data10[y][x] = int(code[get_bin9(x, y)])



for y in range(120):
    data10[y][0] = 1
    data10[y][-1] = 1

for x in range(120):
    data10[0][x] = 1
    data10[-1][x] = 1

for y in range(120):
    data10[y].insert(0, 1)
    data10[y].append(1)

data10.insert(0, [1 for _ in range(122)])
data10.append([1 for _ in range(122)])

def get_bin10(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data10[y+dy][x+dx])
    return int(b, 2)

data11 = [[0 for _ in range(122)] for _ in range(122)]

for y in range(1, 121):
    for x in range(1, 121):
        data11[y][x] = int(code[get_bin10(x, y)])

for y in range(122):
    data11[y].insert(0, 0)
    data11[y].append(0)

data11.insert(0, [0 for _ in range(124)])
data11.append([0 for _ in range(124)])

def get_bin11(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data11[y+dy][x+dx])
    return int(b, 2)

data12 = [[0 for _ in range(124)] for _ in range(124)]

for y in range(1, 123):
    for x in range(1, 123):
        data12[y][x] = int(code[get_bin11(x, y)])



for y in range(124):
    data12[y][0] = 1
    data12[y][-1] = 1

for x in range(124):
    data12[0][x] = 1
    data12[-1][x] = 1

for y in range(124):
    data12[y].insert(0, 1)
    data12[y].append(1)

data12.insert(0, [1 for _ in range(126)])
data12.append([1 for _ in range(126)])

def get_bin12(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data12[y+dy][x+dx])
    return int(b, 2)

data13 = [[0 for _ in range(126)] for _ in range(126)]

for y in range(1, 125):
    for x in range(1, 125):
        data13[y][x] = int(code[get_bin12(x, y)])

for y in range(126):
    data13[y].insert(0, 0)
    data13[y].append(0)

data13.insert(0, [0 for _ in range(128)])
data13.append([0 for _ in range(128)])

def get_bin13(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data13[y+dy][x+dx])
    return int(b, 2)

data14 = [[0 for _ in range(128)] for _ in range(128)]

for y in range(1, 127):
    for x in range(1, 127):
        data14[y][x] = int(code[get_bin13(x, y)])



for y in range(128):
    data14[y][0] = 1
    data14[y][-1] = 1

for x in range(128):
    data14[0][x] = 1
    data14[-1][x] = 1

for y in range(128):
    data14[y].insert(0, 1)
    data14[y].append(1)

data14.insert(0, [1 for _ in range(130)])
data14.append([1 for _ in range(130)])

def get_bin14(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data14[y+dy][x+dx])
    return int(b, 2)

data15 = [[0 for _ in range(130)] for _ in range(130)]

for y in range(1, 129):
    for x in range(1, 129):
        data15[y][x] = int(code[get_bin14(x, y)])

for y in range(130):
    data15[y].insert(0, 0)
    data15[y].append(0)

data15.insert(0, [0 for _ in range(132)])
data15.append([0 for _ in range(132)])

def get_bin15(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data15[y+dy][x+dx])
    return int(b, 2)

data16 = [[0 for _ in range(132)] for _ in range(132)]

for y in range(1, 131):
    for x in range(1, 131):
        data16[y][x] = int(code[get_bin15(x, y)])



for y in range(132):
    data16[y][0] = 1
    data16[y][-1] = 1

for x in range(132):
    data16[0][x] = 1
    data16[-1][x] = 1

for y in range(132):
    data16[y].insert(0, 1)
    data16[y].append(1)

data16.insert(0, [1 for _ in range(134)])
data16.append([1 for _ in range(134)])

def get_bin16(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data16[y+dy][x+dx])
    return int(b, 2)

data17 = [[0 for _ in range(134)] for _ in range(134)]

for y in range(1, 133):
    for x in range(1, 133):
        data17[y][x] = int(code[get_bin16(x, y)])

for y in range(134):
    data17[y].insert(0, 0)
    data17[y].append(0)

data17.insert(0, [0 for _ in range(136)])
data17.append([0 for _ in range(136)])

def get_bin17(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data17[y+dy][x+dx])
    return int(b, 2)

data18 = [[0 for _ in range(136)] for _ in range(136)]

for y in range(1, 135):
    for x in range(1, 135):
        data18[y][x] = int(code[get_bin17(x, y)])



for y in range(136):
    data18[y][0] = 1
    data18[y][-1] = 1

for x in range(136):
    data18[0][x] = 1
    data18[-1][x] = 1

for y in range(136):
    data18[y].insert(0, 1)
    data18[y].append(1)

data18.insert(0, [1 for _ in range(138)])
data18.append([1 for _ in range(138)])

def get_bin18(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data18[y+dy][x+dx])
    return int(b, 2)

data19 = [[0 for _ in range(138)] for _ in range(138)]

for y in range(1, 137):
    for x in range(1, 137):
        data19[y][x] = int(code[get_bin18(x, y)])

for y in range(138):
    data19[y].insert(0, 0)
    data19[y].append(0)

data19.insert(0, [0 for _ in range(140)])
data19.append([0 for _ in range(140)])

def get_bin19(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data19[y+dy][x+dx])
    return int(b, 2)

data20 = [[0 for _ in range(140)] for _ in range(140)]

for y in range(1, 139):
    for x in range(1, 139):
        data20[y][x] = int(code[get_bin19(x, y)])



for y in range(140):
    data20[y][0] = 1
    data20[y][-1] = 1

for x in range(140):
    data20[0][x] = 1
    data20[-1][x] = 1

for y in range(140):
    data20[y].insert(0, 1)
    data20[y].append(1)

data20.insert(0, [1 for _ in range(142)])
data20.append([1 for _ in range(142)])

def get_bin20(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data20[y+dy][x+dx])
    return int(b, 2)

data21 = [[0 for _ in range(142)] for _ in range(142)]

for y in range(1, 141):
    for x in range(1, 141):
        data21[y][x] = int(code[get_bin20(x, y)])

for y in range(142):
    data21[y].insert(0, 0)
    data21[y].append(0)

data21.insert(0, [0 for _ in range(144)])
data21.append([0 for _ in range(144)])

def get_bin21(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data21[y+dy][x+dx])
    return int(b, 2)

data22 = [[0 for _ in range(144)] for _ in range(144)]

for y in range(1, 143):
    for x in range(1, 143):
        data22[y][x] = int(code[get_bin21(x, y)])



for y in range(144):
    data22[y][0] = 1
    data22[y][-1] = 1

for x in range(144):
    data22[0][x] = 1
    data22[-1][x] = 1

for y in range(144):
    data22[y].insert(0, 1)
    data22[y].append(1)

data22.insert(0, [1 for _ in range(146)])
data22.append([1 for _ in range(146)])

def get_bin22(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data22[y+dy][x+dx])
    return int(b, 2)

data23 = [[0 for _ in range(146)] for _ in range(146)]

for y in range(1, 145):
    for x in range(1, 145):
        data23[y][x] = int(code[get_bin22(x, y)])

for y in range(146):
    data23[y].insert(0, 0)
    data23[y].append(0)

data23.insert(0, [0 for _ in range(148)])
data23.append([0 for _ in range(148)])

def get_bin23(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data23[y+dy][x+dx])
    return int(b, 2)

data24 = [[0 for _ in range(148)] for _ in range(148)]

for y in range(1, 147):
    for x in range(1, 147):
        data24[y][x] = int(code[get_bin23(x, y)])



for y in range(148):
    data24[y][0] = 1
    data24[y][-1] = 1

for x in range(148):
    data24[0][x] = 1
    data24[-1][x] = 1

for y in range(148):
    data24[y].insert(0, 1)
    data24[y].append(1)

data24.insert(0, [1 for _ in range(150)])
data24.append([1 for _ in range(150)])

def get_bin24(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data24[y+dy][x+dx])
    return int(b, 2)

data25 = [[0 for _ in range(150)] for _ in range(150)]

for y in range(1, 149):
    for x in range(1, 149):
        data25[y][x] = int(code[get_bin24(x, y)])

for y in range(150):
    data25[y].insert(0, 0)
    data25[y].append(0)

data25.insert(0, [0 for _ in range(152)])
data25.append([0 for _ in range(152)])

def get_bin25(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data25[y+dy][x+dx])
    return int(b, 2)

data26 = [[0 for _ in range(152)] for _ in range(152)]

for y in range(1, 151):
    for x in range(1, 151):
        data26[y][x] = int(code[get_bin25(x, y)])



for y in range(152):
    data26[y][0] = 1
    data26[y][-1] = 1

for x in range(152):
    data26[0][x] = 1
    data26[-1][x] = 1

for y in range(152):
    data26[y].insert(0, 1)
    data26[y].append(1)

data26.insert(0, [1 for _ in range(154)])
data26.append([1 for _ in range(154)])

def get_bin26(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data26[y+dy][x+dx])
    return int(b, 2)

data27 = [[0 for _ in range(154)] for _ in range(154)]

for y in range(1, 153):
    for x in range(1, 153):
        data27[y][x] = int(code[get_bin26(x, y)])

for y in range(154):
    data27[y].insert(0, 0)
    data27[y].append(0)

data27.insert(0, [0 for _ in range(156)])
data27.append([0 for _ in range(156)])

def get_bin27(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data27[y+dy][x+dx])
    return int(b, 2)

data28 = [[0 for _ in range(156)] for _ in range(156)]

for y in range(1, 155):
    for x in range(1, 155):
        data28[y][x] = int(code[get_bin27(x, y)])



for y in range(156):
    data28[y][0] = 1
    data28[y][-1] = 1

for x in range(156):
    data28[0][x] = 1
    data28[-1][x] = 1

for y in range(156):
    data28[y].insert(0, 1)
    data28[y].append(1)

data28.insert(0, [1 for _ in range(158)])
data28.append([1 for _ in range(158)])

def get_bin28(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data28[y+dy][x+dx])
    return int(b, 2)

data29 = [[0 for _ in range(158)] for _ in range(158)]

for y in range(1, 157):
    for x in range(1, 157):
        data29[y][x] = int(code[get_bin28(x, y)])

for y in range(158):
    data29[y].insert(0, 0)
    data29[y].append(0)

data29.insert(0, [0 for _ in range(160)])
data29.append([0 for _ in range(160)])

def get_bin29(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data29[y+dy][x+dx])
    return int(b, 2)

data30 = [[0 for _ in range(160)] for _ in range(160)]

for y in range(1, 159):
    for x in range(1, 159):
        data30[y][x] = int(code[get_bin29(x, y)])



for y in range(160):
    data30[y][0] = 1
    data30[y][-1] = 1

for x in range(160):
    data30[0][x] = 1
    data30[-1][x] = 1

for y in range(160):
    data30[y].insert(0, 1)
    data30[y].append(1)

data30.insert(0, [1 for _ in range(162)])
data30.append([1 for _ in range(162)])

def get_bin30(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data30[y+dy][x+dx])
    return int(b, 2)

data31 = [[0 for _ in range(162)] for _ in range(162)]

for y in range(1, 161):
    for x in range(1, 161):
        data31[y][x] = int(code[get_bin30(x, y)])

for y in range(162):
    data31[y].insert(0, 0)
    data31[y].append(0)

data31.insert(0, [0 for _ in range(164)])
data31.append([0 for _ in range(164)])

def get_bin31(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data31[y+dy][x+dx])
    return int(b, 2)

data32 = [[0 for _ in range(164)] for _ in range(164)]

for y in range(1, 163):
    for x in range(1, 163):
        data32[y][x] = int(code[get_bin31(x, y)])



for y in range(164):
    data32[y][0] = 1
    data32[y][-1] = 1

for x in range(164):
    data32[0][x] = 1
    data32[-1][x] = 1

for y in range(164):
    data32[y].insert(0, 1)
    data32[y].append(1)

data32.insert(0, [1 for _ in range(166)])
data32.append([1 for _ in range(166)])

def get_bin32(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data32[y+dy][x+dx])
    return int(b, 2)

data33 = [[0 for _ in range(166)] for _ in range(166)]

for y in range(1, 165):
    for x in range(1, 165):
        data33[y][x] = int(code[get_bin32(x, y)])

for y in range(166):
    data33[y].insert(0, 0)
    data33[y].append(0)

data33.insert(0, [0 for _ in range(168)])
data33.append([0 for _ in range(168)])

def get_bin33(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data33[y+dy][x+dx])
    return int(b, 2)

data34 = [[0 for _ in range(168)] for _ in range(168)]

for y in range(1, 167):
    for x in range(1, 167):
        data34[y][x] = int(code[get_bin33(x, y)])



for y in range(168):
    data34[y][0] = 1
    data34[y][-1] = 1

for x in range(168):
    data34[0][x] = 1
    data34[-1][x] = 1

for y in range(168):
    data34[y].insert(0, 1)
    data34[y].append(1)

data34.insert(0, [1 for _ in range(170)])
data34.append([1 for _ in range(170)])

def get_bin34(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data34[y+dy][x+dx])
    return int(b, 2)

data35 = [[0 for _ in range(170)] for _ in range(170)]

for y in range(1, 169):
    for x in range(1, 169):
        data35[y][x] = int(code[get_bin34(x, y)])

for y in range(170):
    data35[y].insert(0, 0)
    data35[y].append(0)

data35.insert(0, [0 for _ in range(172)])
data35.append([0 for _ in range(172)])

def get_bin35(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data35[y+dy][x+dx])
    return int(b, 2)

data36 = [[0 for _ in range(172)] for _ in range(172)]

for y in range(1, 171):
    for x in range(1, 171):
        data36[y][x] = int(code[get_bin35(x, y)])



for y in range(172):
    data36[y][0] = 1
    data36[y][-1] = 1

for x in range(172):
    data36[0][x] = 1
    data36[-1][x] = 1

for y in range(172):
    data36[y].insert(0, 1)
    data36[y].append(1)

data36.insert(0, [1 for _ in range(174)])
data36.append([1 for _ in range(174)])

def get_bin36(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data36[y+dy][x+dx])
    return int(b, 2)

data37 = [[0 for _ in range(174)] for _ in range(174)]

for y in range(1, 173):
    for x in range(1, 173):
        data37[y][x] = int(code[get_bin36(x, y)])

for y in range(174):
    data37[y].insert(0, 0)
    data37[y].append(0)

data37.insert(0, [0 for _ in range(176)])
data37.append([0 for _ in range(176)])

def get_bin37(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data37[y+dy][x+dx])
    return int(b, 2)

data38 = [[0 for _ in range(176)] for _ in range(176)]

for y in range(1, 175):
    for x in range(1, 175):
        data38[y][x] = int(code[get_bin37(x, y)])



for y in range(176):
    data38[y][0] = 1
    data38[y][-1] = 1

for x in range(176):
    data38[0][x] = 1
    data38[-1][x] = 1

for y in range(176):
    data38[y].insert(0, 1)
    data38[y].append(1)

data38.insert(0, [1 for _ in range(178)])
data38.append([1 for _ in range(178)])

def get_bin38(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data38[y+dy][x+dx])
    return int(b, 2)

data39 = [[0 for _ in range(178)] for _ in range(178)]

for y in range(1, 177):
    for x in range(1, 177):
        data39[y][x] = int(code[get_bin38(x, y)])

for y in range(178):
    data39[y].insert(0, 0)
    data39[y].append(0)

data39.insert(0, [0 for _ in range(180)])
data39.append([0 for _ in range(180)])

def get_bin39(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data39[y+dy][x+dx])
    return int(b, 2)

data40 = [[0 for _ in range(180)] for _ in range(180)]

for y in range(1, 179):
    for x in range(1, 179):
        data40[y][x] = int(code[get_bin39(x, y)])



for y in range(180):
    data40[y][0] = 1
    data40[y][-1] = 1

for x in range(180):
    data40[0][x] = 1
    data40[-1][x] = 1

for y in range(180):
    data40[y].insert(0, 1)
    data40[y].append(1)

data40.insert(0, [1 for _ in range(182)])
data40.append([1 for _ in range(182)])

def get_bin40(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data40[y+dy][x+dx])
    return int(b, 2)

data41 = [[0 for _ in range(182)] for _ in range(182)]

for y in range(1, 181):
    for x in range(1, 181):
        data41[y][x] = int(code[get_bin40(x, y)])

for y in range(182):
    data41[y].insert(0, 0)
    data41[y].append(0)

data41.insert(0, [0 for _ in range(184)])
data41.append([0 for _ in range(184)])

def get_bin41(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data41[y+dy][x+dx])
    return int(b, 2)

data42 = [[0 for _ in range(184)] for _ in range(184)]

for y in range(1, 183):
    for x in range(1, 183):
        data42[y][x] = int(code[get_bin41(x, y)])



for y in range(184):
    data42[y][0] = 1
    data42[y][-1] = 1

for x in range(184):
    data42[0][x] = 1
    data42[-1][x] = 1

for y in range(184):
    data42[y].insert(0, 1)
    data42[y].append(1)

data42.insert(0, [1 for _ in range(186)])
data42.append([1 for _ in range(186)])

def get_bin42(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data42[y+dy][x+dx])
    return int(b, 2)

data43 = [[0 for _ in range(186)] for _ in range(186)]

for y in range(1, 185):
    for x in range(1, 185):
        data43[y][x] = int(code[get_bin42(x, y)])

for y in range(186):
    data43[y].insert(0, 0)
    data43[y].append(0)

data43.insert(0, [0 for _ in range(188)])
data43.append([0 for _ in range(188)])

def get_bin43(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data43[y+dy][x+dx])
    return int(b, 2)

data44 = [[0 for _ in range(188)] for _ in range(188)]

for y in range(1, 187):
    for x in range(1, 187):
        data44[y][x] = int(code[get_bin43(x, y)])



for y in range(188):
    data44[y][0] = 1
    data44[y][-1] = 1

for x in range(188):
    data44[0][x] = 1
    data44[-1][x] = 1

for y in range(188):
    data44[y].insert(0, 1)
    data44[y].append(1)

data44.insert(0, [1 for _ in range(190)])
data44.append([1 for _ in range(190)])

def get_bin44(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data44[y+dy][x+dx])
    return int(b, 2)

data45 = [[0 for _ in range(190)] for _ in range(190)]

for y in range(1, 189):
    for x in range(1, 189):
        data45[y][x] = int(code[get_bin44(x, y)])

for y in range(190):
    data45[y].insert(0, 0)
    data45[y].append(0)

data45.insert(0, [0 for _ in range(192)])
data45.append([0 for _ in range(192)])

def get_bin45(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data45[y+dy][x+dx])
    return int(b, 2)

data46 = [[0 for _ in range(192)] for _ in range(192)]

for y in range(1, 191):
    for x in range(1, 191):
        data46[y][x] = int(code[get_bin45(x, y)])



for y in range(192):
    data46[y][0] = 1
    data46[y][-1] = 1

for x in range(192):
    data46[0][x] = 1
    data46[-1][x] = 1

for y in range(192):
    data46[y].insert(0, 1)
    data46[y].append(1)

data46.insert(0, [1 for _ in range(194)])
data46.append([1 for _ in range(194)])

def get_bin46(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data46[y+dy][x+dx])
    return int(b, 2)

data47 = [[0 for _ in range(194)] for _ in range(194)]

for y in range(1, 193):
    for x in range(1, 193):
        data47[y][x] = int(code[get_bin46(x, y)])

for y in range(194):
    data47[y].insert(0, 0)
    data47[y].append(0)

data47.insert(0, [0 for _ in range(196)])
data47.append([0 for _ in range(196)])

def get_bin47(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data47[y+dy][x+dx])
    return int(b, 2)

data48 = [[0 for _ in range(196)] for _ in range(196)]

for y in range(1, 195):
    for x in range(1, 195):
        data48[y][x] = int(code[get_bin47(x, y)])



for y in range(196):
    data48[y][0] = 1
    data48[y][-1] = 1

for x in range(196):
    data48[0][x] = 1
    data48[-1][x] = 1

for y in range(196):
    data48[y].insert(0, 1)
    data48[y].append(1)

data48.insert(0, [1 for _ in range(198)])
data48.append([1 for _ in range(198)])

def get_bin48(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data48[y+dy][x+dx])
    return int(b, 2)

data49 = [[0 for _ in range(198)] for _ in range(198)]

for y in range(1, 197):
    for x in range(1, 197):
        data49[y][x] = int(code[get_bin48(x, y)])

for y in range(198):
    data49[y].insert(0, 0)
    data49[y].append(0)

data49.insert(0, [0 for _ in range(200)])
data49.append([0 for _ in range(200)])

def get_bin49(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data49[y+dy][x+dx])
    return int(b, 2)

data50 = [[0 for _ in range(200)] for _ in range(200)]

for y in range(1, 199):
    for x in range(1, 199):
        data50[y][x] = int(code[get_bin49(x, y)])



for y in range(200):
    data50[y][0] = 1
    data50[y][-1] = 1

for x in range(200):
    data50[0][x] = 1
    data50[-1][x] = 1

for y in range(200):
    data50[y].insert(0, 1)
    data50[y].append(1)

data50.insert(0, [1 for _ in range(202)])
data50.append([1 for _ in range(202)])

def get_bin50(x, y):
    b = ''
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            b += str(data50[y+dy][x+dx])
    return int(b, 2)

data51 = [[0 for _ in range(202)] for _ in range(202)]

for y in range(1, 201):
    for x in range(1, 201):
        data51[y][x] = int(code[get_bin50(x, y)])

for y in range(202):
    data51[y].insert(0, 0)
    data51[y].append(0)

data51.insert(0, [0 for _ in range(204)])
data51.append([0 for _ in range(204)])


print(sum(sum(row) for row in data51))

