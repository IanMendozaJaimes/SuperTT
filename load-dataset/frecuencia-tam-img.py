import os
import cv2
import matplotlib.pyplot as plt
from itertools import islice
from collections import OrderedDict 

TRAINING_PATH='./CROHME_dataset_v5/converted_expressions/'
img_list = os.listdir(TRAINING_PATH)
width_dict = {}
height_dict = {}

def take(n, iterable):
    return list(islice(iterable, n))

for img in img_list:
    img = TRAINING_PATH + img
    im = cv2.imread(img, cv2.IMREAD_GRAYSCALE)
    h, w = im.shape
    if h in height_dict:
        height_dict[h] = height_dict[h]+1
    else:
        height_dict[h] = 1
    if w in width_dict:
        width_dict[w] = width_dict[w]+1
    else:
        width_dict[w] = 1

width_dict = OrderedDict(sorted(width_dict.items(), key = lambda kv:(kv[1], kv[0]), reverse=True))
height_dict = OrderedDict(sorted(height_dict.items(), key = lambda kv:(kv[1], kv[0]), reverse=True))
print(len(img_list))
print('(width, frecuencia)')
for i in width_dict:
    print(i, width_dict[i])
print('-'*20)
print('(height, frecuencia)')
for i in height_dict:
    print(i, height_dict[i])
#plt.bar(range(len(width_dict)), list(width_dict.values()), align='center')
#plt.xticks(range(len(width_dict)), list(width_dict.keys()))
#plt.show()
#print(height_dict)