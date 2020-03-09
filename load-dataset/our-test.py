import tensorflow as tf
file_path = './training.csv'
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

show_batch(dataset)

#for element in dataset:
#	print(element)
