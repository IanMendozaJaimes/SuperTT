import tensorflow as tf
file_path = './training.csv'
AUTOTUNE = tf.data.experimental.AUTOTUNE
dataset = tf.data.experimental.make_csv_dataset(
      file_path,
      batch_size=2, # Artificially small to make examples easier to show.
      label_name="expresion",
      na_value="?",
      num_epochs=1,
      ignore_errors=True)

def show_batch(dataset):
  for batch, label in dataset:
    for key, value in batch.items():
      print("{:20s}: {}".format(key, value.numpy()))

def process_path(features, label):
	#label = get_label(file_path)
	#print(label)
	# load the raw data from the file as a string
	#img = tf.io.read_file(file_path)
	#img = decode_img(img)
	return tf.stack(list(features.values()), axis=-1), label


#show_batch(dataset)
dataset = dataset.map(process_path, num_parallel_calls=AUTOTUNE)
for features, labels in dataset:
	print(features)
	print()
	print(labels)