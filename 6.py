with open('inputs/6.txt', 'r') as fi:
    text = fi.read()

found_start_of_packet = False
for i in range(len(text) - 14):
    if not found_start_of_packet and len(set(text[i:i + 4])) == 4:
        print(i + 4)
        found_start_of_packet = True
    
    if len(set(text[i:i + 14])) == 14:
        print(i + 14)
        break

