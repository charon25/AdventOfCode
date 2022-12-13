import re


class Packet:
    def __init__(self) -> None:
        self.items: list['Packet'] = list()

    def cmp(self, other: 'Packet') -> None:
        for k in range(len(self.items)):
            if k >= len(other.items):
                return 1

            if type(self.items[k]) == Packet and type(other.items[k]) == Packet:
                comp = self.items[k].cmp(other.items[k])
                if comp != 0:
                    return comp
            
            elif type(self.items[k]) == int and type(other.items[k]) == int:
                if self.items[k] < other.items[k]:
                    return -1
                if self.items[k] > other.items[k]:
                    return 1

            else:
                if type(self.items[k]) == int:
                    new_item = Packet()
                    new_item.items.append(self.items[k])
                    comp = new_item.cmp(other.items[k])
                    if comp != 0:
                        return comp
                else:
                    new_item = Packet()
                    new_item.items.append(other.items[k])
                    comp = self.items[k].cmp(new_item)
                    if comp != 0:
                        return comp

        if len(self.items) < len(other.items):
            return -1

        return 0

    def __lt__(self, other: 'Packet'):
        return self.cmp(other) < 0

    def __eq__(self, other: 'Packet') -> bool:
        return self.cmp(other) == 0

    def __repr__(self) -> str:
        return f'[{", ".join(map(repr, self.items))}]'

with open('inputs/13.txt', 'r') as fi:
    text = fi.read()

def convert_to_packet(string: str) -> tuple[Packet, int]:
    if string.isnumeric():
        packet = Packet()
        packet.items = [int(string)]
        return (packet, len(string))
    if re.match(r'\[(?:\d+,? ?)+\]', string):
        packet = Packet()
        packet.items = list(map(int, string[1:-1].split(',')))
        return (packet, len(string))

    packet = Packet()

    depths = []
    depth = 0
    for c in string:
        if c == ']':
            depth -= 1
        depths.append(depth)
        if c == '[':
            depth += 1

    index = 1
    while index < len(string) - 1:
        c = string[index]
        if c == '[':
            end_bracket = index + 1 + depths[index+1:].index(depths[index])
            sub_packet, length = convert_to_packet(string[index:end_bracket+1])
            packet.items.append(sub_packet)
            index += length
        elif c in '0123456789':
            d = 0
            while index + d < len(string) and string[index + d] in '0123456789':
                d += 1
            packet.items.append(int(string[index:index+d]))
            index += d
        elif c in ', ':
            index += 1

    return (packet, len(string))

# Part 1
pairs = [(convert_to_packet(pair.split()[0])[0], convert_to_packet(pair.split()[1])[0]) for pair in text.split("\n\n")]

total_sum = 0

for k, (p1, p2) in enumerate(pairs):
    if p1.cmp(p2) < 0:
        total_sum += (k + 1)

print(total_sum)

# Part 2
divider1 = convert_to_packet('[[2]]')[0]
divider2 = convert_to_packet('[[6]]')[0]
all_packets = [divider1, divider2]
for pair in pairs:
    all_packets.extend(pair)

all_packets.sort()

product = 1

for k, packet in enumerate(all_packets):
    if packet == divider1:
        product *= (k + 1)
    elif packet == divider2:
        product *= (k + 1)

print(product)
