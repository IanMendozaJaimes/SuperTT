import csv
import numpy as np
ARCHIVO = './CROHME_dataset_test_v4/tokenized_test.csv'
ARCHIVO_NUEVO = './CROHME_dataset_test_v4/tokenized.csv'
original = open(ARCHIVO, 'r')

csv_reader = csv.reader(original, delimiter=',')
maximo = 0
for row in csv_reader:
    label = row[1]
    label = label.strip()
    label = label.split(' ')
    if (len(label) > maximo):
        maximo = len(label)

print(maximo)
original.close()
original = open(ARCHIVO, 'r')
copia = open(ARCHIVO_NUEVO, 'w')
csv_writer = csv.writer(copia, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
csv_reader = csv.reader(original, delimiter=',')
for row in csv_reader:
    label = row[1]
    label = label.strip()
    label = '1000 ' + label + ' 1001'
    label = label.split(' ')
    cantidad_agregar = maximo + 2 - len(label)
    label = np.pad(label, (0, cantidad_agregar), 'constant')
    row[1] = ' '.join(str(x) for x in label)
    csv_writer.writerow(row)

original.close()
copia.close()