import tensorflow as tf

training_path = './CROHME_dataset_v1'
training_file_name = 'tokenized.csv'
training_file_path = training_path + '/' + training_file_name
BATCH_SIZE = 100
IMG_HEIGHT = 300
IMG_WIDTH = 300
AUTOTUNE = tf.data.experimental.AUTOTUNE

def show_batch(dataset):
	for batch, label in dataset:
		print(label)
		for key, value in batch.items():
			print("{:20s}: {}".format(key, value.numpy()))
		print()

def process_path(features, label):
	prueba = features['imagen']
	prueba = training_path + '/' + prueba
	img = tf.io.read_file(prueba[0])
	img = tf.image.decode_jpeg(img, channels=1)
	img = tf.image.convert_image_dtype(img, tf.float32)
	return img, label

dataset = tf.data.experimental.make_csv_dataset(
      training_file_path,
      batch_size=1,
      na_value="?",
      num_epochs=1,
	  column_names=["imagen", "token"],
	  label_name="token",
      ignore_errors=True)


# Conjunto listo
dataset = dataset.map(process_path, num_parallel_calls=AUTOTUNE)
for i in (dataset.take(1)):
	print(i)