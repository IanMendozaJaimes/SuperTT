import csv
import numpy as np
ARCHIVO = './CROHME_dataset_v1/tokenized.csv'
ARCHIVO_V2 = './CROHME_dataset_v1/tokenizedV2.csv'
original = open(ARCHIVO, 'r')

csv_reader = csv.reader(original, delimiter=',')
maximo = 0
for row in csv_reader:
    label = row[1]
    label = label.strip()
    label = label.split(' ')
    if (len(label) > maximo):
        maximo = len(label)

print("-"*90)
print(maximo)
original.close()
original = open(ARCHIVO, 'r')
copia = open(ARCHIVO_V2, 'w')
csv_writer = csv.writer(copia, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
csv_reader = csv.reader(original, delimiter=',')
for row in csv_reader:
    image = row[0]
    label = row[1]
    label = label.strip()
    label = label.split(' ')
    cantidad_agregar = maximo - len(label)
    label = np.pad(label, (0, cantidad_agregar), 'constant')
    row[1] = ' '.join(str(x) for x in label)
    csv_writer.writerow(row)

original.close()
copia.close()