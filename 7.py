class Directory:
    def __init__(self, parent: 'Directory', name: str) -> None:
        self.parent = parent
        self.name = name
        self.children: list[File | 'Directory'] = list()
        self.size = None
    
    def get_dir_child(self, child_name: str) -> 'Directory':
        for child in self.children:
            if type(child) == Directory and child.name == child_name:
                return child

    def get_all_dir_children(self) -> list['Directory']:
        return [child for child in self.children if type(child) == Directory]

    def get_size(self) -> int:
        if not self.size is None:
            return self.size

        total_size = 0
        for child in self.children:
            if type(child) == File:
                total_size += child.size
            else:
                total_size += child.get_size()
        
        self.size = total_size
        return total_size

    def __repr__(self) -> str:
        return f'dir {self.name}'

class File:
    def __init__(self, parent: Directory, name: str, size: int) -> None:
        self.parent = parent
        self.name = name
        self.size = size

    def __repr__(self) -> str:
        return f'file {self.name}:{self.size}'


with open('inputs/7.txt', 'r') as fi:
    lines = fi.read().splitlines()

root = Directory(None, '/')

current_directory: Directory = root

index = 0
while index < len(lines):
    line = lines[index]
    if line.startswith('$ cd'):
        directory = line.split(' ')[2]
        if directory == '..':
            current_directory = current_directory.parent
        elif directory == '/':
            current_directory = root
        else:
            current_directory = current_directory.get_dir_child(directory)
        
        index += 1
    elif line.startswith('$ ls'):
        index += 1
        while index < len(lines) and lines[index][0] != '$':
            type_size, name = lines[index].split(' ')
            if type_size == 'dir':
                current_directory.children.append(Directory(current_directory, name))
            else:
                current_directory.children.append(File(current_directory, name, int(type_size)))
            index += 1

# Compute the size of every directory recursively
root.get_size()

# BFS
all_directories: list[Directory] = []
Q = [root]
while len(Q) > 0:
    current = Q.pop()
    all_directories.append(current)
    for child in current.get_all_dir_children():
        Q.append(child)

# Part 1
print(sum(directory.get_size() for directory in all_directories if directory.get_size() <= 100000))

# Part 2
TOTAL_SPACE = 70000000
FREE_SPACE = TOTAL_SPACE - root.get_size()
NEEDED_SPACE = 30000000 - FREE_SPACE
print(min(directory.get_size() for directory in all_directories if directory.get_size() >= NEEDED_SPACE))
