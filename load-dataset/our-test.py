import tensorflow as tf
import numpy as np

training_path = './CROHME_dataset_v1'
training_file_name = '/prueba.csv'
training_file_path = training_path + training_file_name
BATCH_SIZE = 2
IMG_HEIGHT = 5
IMG_WIDTH = 5
AUTOTUNE = tf.data.experimental.AUTOTUNE
COLUMN_NAMES = ['imagen', 'token']
LABEL_NAME = COLUMN_NAMES[-1]

def show_batch(dataset):
	for batch, label in dataset.take(1):
		print(label)
		for key, value in batch.items():
			print("{:20s}: {}".format(key, value.numpy()))
		print()

@tf.function
def procesar_label(label):
	nueva_label = tf.strings.strip(label)
	nueva_label = tf.strings.split(nueva_label, ' ')
	nueva_label = tf.strings.to_number(nueva_label, out_type=tf.dtypes.int32)
	return nueva_label.to_tensor(default_value=0)

def procesar_imagen(imagenes):
	tf.print(imagenes)
	imagenes = tf.strings.join([training_path, '/', imagenes])
	img = tf.io.read_file(imagenes)
	img = tf.image.decode_jpeg(img, channels=1)
	img = tf.image.convert_image_dtype(img, tf.float32)
	img = tf.image.resize(img, [IMG_WIDTH, IMG_HEIGHT])
	#tf.stack(lista)
	return imagenes

def process_path(features, label):
	nueva_label = procesar_label(label)
	features = tf.stack(list(features.values()), axis=1)
	tf.print(features)
	for g in features:
		tf.print(g)
		tf.print('000000000000000')
	# tf.stack(lista, axis=1)
	#procesar_imagen(features.items())
	
	return features, nueva_label

dataset = tf.data.experimental.make_csv_dataset(
      training_file_path,
      batch_size=BATCH_SIZE,
      na_value="?",
      num_epochs=1,
	  column_names=COLUMN_NAMES,
	  label_name=LABEL_NAME,
      ignore_errors=True)
print('----------')
dataset = dataset.map(process_path, num_parallel_calls=1)
print('----------')
#features, labels = next(iter(dataset))
for (batch, (img_tensor, target)) in enumerate(dataset):
	print(batch)
	print(img_tensor)
	# print(target)
	break
