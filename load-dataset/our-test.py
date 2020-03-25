import tensorflow as tf
file_path = './training.csv'
BATCH_SIZE = 32
IMG_HEIGHT = 155
IMG_WIDTH = 515
AUTOTUNE = tf.data.experimental.AUTOTUNE

def show_batch(dataset):
	for batch, label in dataset:
		print(label)
		for key, value in batch.items():
			print("{:20s}: {}".format(key, value.numpy()))
		print()

dataset = tf.data.experimental.make_csv_dataset(
      file_path,
      batch_size=1,
      na_value="?",
      num_epochs=1,
      label_name='expresion',
      ignore_errors=True)

def process_path(features, label):
	prueba = features['imagen']
	img = tf.io.read_file(prueba[0])
	img = tf.image.decode_jpeg(img, channels=1)
	img = tf.image.convert_image_dtype(img, tf.float32)
	img = tf.image.resize(img, [IMG_WIDTH, IMG_HEIGHT])
	return img, label

dataset = dataset.map(process_path, num_parallel_calls=AUTOTUNE)