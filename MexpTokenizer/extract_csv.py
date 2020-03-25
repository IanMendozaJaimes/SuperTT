f = open("training.csv", "r")
f2 = open("file.txt", "w")
for line in f:
    f2.write(line.split(",")[1])
f2.close()
f.close()