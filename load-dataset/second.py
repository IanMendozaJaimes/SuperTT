import tensorflow as tf
import numpy as np

training_path = './CROHME_dataset_v1'
training_file_name = '/tokenizedV2.csv'
training_file_path = training_path + training_file_name
BATCH_SIZE = 100
IMG_HEIGHT = 350
IMG_WIDTH = 350
AUTOTUNE = tf.data.experimental.AUTOTUNE
COLUMN_NAMES = ['imagen', 'label']
LABEL_NAME = COLUMN_NAMES[-1]

def procesar_fila(value):
    columns_default = [[""], [""]]
    columns = tf.io.decode_csv(value, record_defaults=columns_default, field_delim=',')
    features = dict(zip(COLUMN_NAMES, columns))
    for f, tensor in features.items():
        if f == COLUMN_NAMES[0]:
            tensor = tf.strings.join([training_path, '/', tensor])
            img = tf.io.read_file(tensor)
            img = tf.image.decode_jpeg(img, channels=1)
            img = tf.image.convert_image_dtype(img, tf.float32)
            img = tf.image.resize(img, [IMG_WIDTH, IMG_HEIGHT])
            features[f] = img
        elif f == COLUMN_NAMES[-1]:
            nueva_label = tf.strings.strip(tensor)
            nueva_label = tf.strings.split(nueva_label, ' ')
            nueva_label = tf.strings.to_number(nueva_label, out_type=tf.dtypes.int32)
            features[f] = nueva_label
    return features[COLUMN_NAMES[0]], features[COLUMN_NAMES[-1]]

files = tf.data.Dataset.from_tensor_slices([training_file_path])
dataset = files.interleave(tf.data.TextLineDataset)
dataset = dataset.map(procesar_fila, num_parallel_calls=tf.data.experimental.AUTOTUNE)
dataset = dataset.batch(BATCH_SIZE)
for (batch, (img_tensor, target)) in enumerate(dataset):
    print(batch)
    print(img_tensor)
    print(target)
    break