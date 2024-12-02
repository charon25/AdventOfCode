import re


class Monkey:
    def __init__(self, id: int, starting_items: list[int], operation: tuple[str, str | int], divisor: int, send_to: tuple['Monkey', 'Monkey']) -> None:
        self.id = id
        self.items = list(starting_items)
        self.operation = operation
        self.divisor = divisor
        self.send_if_false = send_to[0]
        self.send_if_true = send_to[1]
        self.inspect_count = 0

    def update(self, monkeys: list['Monkey']) -> None:
        self.send_if_true = monkeys[self.send_if_true]
        self.send_if_false = monkeys[self.send_if_false]

    def operate(self, worry: int) -> int:
        if self.operation[0] == '+':
            return worry + (worry if self.operation[1] == 'old' else self.operation[1])
        if self.operation[0] == '*':
            return worry * (worry if self.operation[1] == 'old' else self.operation[1])

    def play(self) -> None:
        while len(self.items) > 0:
            worry = self.items.pop(0)
            worry = self.operate(worry) // 3
            if worry % self.divisor == 0:
                self.send_if_true.items.append(worry)
            else:
                self.send_if_false.items.append(worry)
            self.inspect_count += 1

    def __repr__(self) -> str:
        return f'Monkey {self.id}: {", ".join(map(str, self.items))}'


monkeys: list[Monkey] = list()

with open('inputs/11.txt') as fi:
    for line in fi:
        line = line.strip()
        if not line:
            monkey = Monkey(monkey_id, starting_items, operation, divisor, (send_if_false, send_if_true))
            monkeys.append(monkey)
        if line.startswith('Monkey'):
            monkey_id = line.split()[-1][:-1]
        elif line.startswith('Starting items'):
            starting_items = list(map(int, line.replace(',', '').split(': ')[1].split()))
        elif line.startswith('Operation'):
            op, operand2 = re.findall(r'new = old (.) (old|\d+)', line)[0]
            operation = (op, operand2 if operand2 == 'old' else int(operand2))
        elif line.startswith('Test'):
            divisor = int(line.split()[-1])
        elif line.startswith('If true'):
            send_if_true = int(line.split()[-1])
        elif line.startswith('If false'):
            send_if_false = int(line.split()[-1])

    # For the last monkey
    monkey = Monkey(monkey_id, starting_items, operation, divisor, (send_if_false, send_if_true))
    monkeys.append(monkey)


monkeys.sort(key=lambda monkey:monkey.id)
for monkey in monkeys:
    monkey.update(monkeys)

for round in range(20):
    for monkey in monkeys:
        monkey.play()

total_inspections = sorted([monkey.inspect_count for monkey in monkeys], reverse=True)

print(total_inspections[0] * total_inspections[1])
