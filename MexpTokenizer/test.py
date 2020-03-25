l = ['x', 'a', 'n', 'y', 'b', 'z', 'd', 'i', 'c', 'k', 't', 'e', 'f', 'p', 'm', 'r', 'j']
l = l + ['g', 'u', 'v', 'q', 'h', 's', 'w', 'l', 'o']

l = ['C', 'B', 'X', 'A', 'F', 'R', 'E', 'L', 'N', 'S', 'P', 'V', 'T', 'M', 'Y', 'H', 'G', 'I']
f = open("letters.txt","w")
for i in l:
    f.write('def t_'+i+'(t):\n')
    f.write('\t\''+i+'\'\n')
    f.write('\treturn t\n')
f.close()