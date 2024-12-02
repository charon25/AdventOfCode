def get_input(day):
    with open(f'inputs\\{day}.txt') as fi:
        return fi.read().splitlines()

lines = get_input(16)
import textwrap

CODES = {c: bin(int(c, 16))[2:].zfill(4) for c in '0123456789ABCDEF'}

class Litteral:
    def __init__(self, b) -> None:
        self.version = int(b[0:3], 2)
        self.type_id = 4
        self.value = ''
        self.left = ''
        part = True
        for digit in textwrap.wrap(b[6:], 5):
            if part:
                self.value += digit[1:]
            else:
                self.left += digit
            if digit[0] == '0':
                part = False
        self.value = int(self.value, 2)
        
    def get_version_sum(self):
        return self.version
    
    def get_value(self):
        return self.value
    
    def __str__(self) -> str:
        return f'Litteral (Ver : {self.version} | Val : {self.value})'
    def __repr__(self) -> str:
        return f'Litteral (Ver : {self.version} | Val : {self.value})'

class Operator:
    def __init__(self, b) -> None:
        self.version = int(b[0:3], 2)
        self.type = int(b[3:6], 2)
        length_type = b[6]
        self.subpackets = []
        if length_type == '0':
            length = int(b[7:22], 2)
            sub_b = b[22:22+length]
            while len(sub_b) > 0:
                if sub_b[3:6] == '100':
                    litt = Litteral(sub_b)
                    self.subpackets.append(litt)
                    sub_b = litt.left
                else:
                    oper = Operator(sub_b)
                    self.subpackets.append(oper)
                    sub_b = oper.left
            self.left = b[22+length:]
        else:
            subpackets_count = int(b[7:18], 2)
            sub_b = b[18:]
            for _ in range(subpackets_count):
                if sub_b[3:6] == '100':
                    litt = Litteral(sub_b)
                    self.subpackets.append(litt)
                    sub_b = litt.left
                else:
                    oper = Operator(sub_b)
                    self.subpackets.append(oper)
                    sub_b = oper.left
            self.left = sub_b
    
    def get_version_sum(self):
        return self.version + sum(subpacket.get_version_sum() for subpacket in self.subpackets)
    
    def get_value(self):
        if self.type == 0:
            return sum(subpacket.get_value() for subpacket in self.subpackets)
        elif self.type == 1:
            value = 1
            for subpacket in self.subpackets:
                value *= subpacket.get_value()
            return value
        elif self.type == 2:
            return min(subpacket.get_value() for subpacket in self.subpackets)
        elif self.type == 3:
            return max(subpacket.get_value() for subpacket in self.subpackets)
        elif self.type == 5:
            return int(self.subpackets[0].get_value() > self.subpackets[1].get_value())
        elif self.type == 6:
            return int(self.subpackets[0].get_value() < self.subpackets[1].get_value())
        elif self.type == 7:
            return int(self.subpackets[0].get_value() == self.subpackets[1].get_value())
    
    def __str__(self) -> str:
        return f'Operator - Version : {self.version} | Type : {self.type} | Subpackets : {self.subpackets}'
    def __repr__(self) -> str:
        return f'Operator - Version : {self.version} | Type : {self.type} | Subpackets : {self.subpackets}'

hexa = lines[0]
b = ''.join(CODES[c] for c in hexa)

o = Operator(b)

print('Version sum :', o.get_version_sum())
print('Value : ', o.get_value())

