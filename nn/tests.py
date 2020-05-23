from __future__ import absolute_import, division, print_function, unicode_literals

import tensorflow as tf
import numpy as np
# import matplotlib.pyplot as plt

# from model import *


# BATCH_SIZE = 2
# UNITS = 256
# EMBEDDING_DIM = 256
# VOCAB_SIZE = 111
# ATTENTION_DIM = 256
# K = 10
# Q_WIDTH = 100
# END = VOCAB_SIZE + 1
# begin = tf.constant(1000)

# IMG_HEIGHT = 480
# IMG_WIDTH = 640


# def load_image(image_path):
#     img = tf.io.read_file(image_path)
#     img = tf.image.decode_png(img, channels=1)
#     img = tf.image.convert_image_dtype(img, tf.float32)

#     img = tf.math.abs(img - 1)

#     img = tf.image.resize(img, (IMG_HEIGHT, IMG_WIDTH))
#     return img



# image_url = './exampleImages/1.png'
# temp_input = tf.expand_dims(load_image(image_url), 0)

# plt.imshow(tf.squeeze(temp_input).numpy(), cmap='gray')
# plt.show()

# print(temp_input)


# encoder = Encoder(name='ENCODER')
# decoder = Decoder(UNITS, EMBEDDING_DIM, VOCAB_SIZE + 2, ATTENTION_DIM, K, Q_WIDTH)

# y = encoder(temp_input, training=True)

# a = y[0].numpy()

# for i in range(len(a)):
#     print(str(i) + ':', end='')
#     for j in range(len(a[0])):
#         print(str(a[i][j]), end=' ')
#     print('')



# a = tf.expand_dims([2] * 3, 1)
# print(a)



# def get_positional_encoding_2d(height, width, d_model):
    
#     pe = np.zeros((height, width, d_model))
#     d_model = int(d_model / 2)
#     h_vector = np.arange(height)
#     w_vector = np.expand_dims(np.arange(width), axis=1)

#     div_term = np.arange(d_model) // 2
#     div_term = np.exp((-2*div_term / d_model) * np.log(10000.0))
    
#     pe[:, :, 0:d_model:2] = np.sin((pe[:, :, 0:d_model:2] + div_term[0::2]) * w_vector)
#     pe[:, :, 1:d_model:2] = np.cos((pe[:, :, 1:d_model:2] + div_term[1::2]) * w_vector)
#     pe[:, :, d_model::2] = np.sin((pe[:, :, d_model::2] + div_term[0::2]) * h_vector[:, np.newaxis, np.newaxis])
#     pe[:, :, d_model+1::2] = np.cos((pe[:, :, d_model+1::2] + div_term[0::2]) * h_vector[:, np.newaxis, np.newaxis])

#     return pe


# a = tf.ones((2,1,3,4))
# pe = get_positional_encoding_2d(1,3,4)

# print(a+pe)

# a = tf.ones((1,6,1))
# print(a)

# Q = tf.keras.layers.Conv1D(filters=2, kernel_size=5, padding="same", use_bias=False)

# print(Q(a))


EMBEDDING_DIM = 5
UNITS = 10
VOCAB_SIZE = 4
END = 4
BEGIN = 4
BATCH_SIZE = 2

def get_num_token_end_expanded(values):
  end_tensor = tf.expand_dims([END] * values.shape[0], 1)
  return tf.where(tf.less_equal(values, end_tensor), values, end_tensor)

def get_num_token_end(values):
  end_tensor = tf.ones((values.shape[0]), dtype=tf.dtypes.int32) * END
  return tf.where(tf.less_equal(values, end_tensor), values, end_tensor)


embedding = tf.keras.layers.Embedding(5, 4)

target = tf.constant([[1, 2, 4, 0], [2, 3, 3, 4]])
t = target[:,0]


is_done = tf.zeros((BATCH_SIZE,), dtype=tf.int32)

end_tensor = tf.constant([4] * BATCH_SIZE)

is_done = tf.where(tf.less(t, end_tensor), is_done, end_tensor)

print(tf.constant([BEGIN] * BATCH_SIZE))

# print(t)
# print(is_done)
# print(end_tensor)

# t = target[:,2]
# is_done = tf.where(tf.less(t, end_tensor), is_done, end_tensor)

# print(t)
# print(is_done)

# t = target[:,3]
# is_done = tf.where(tf.less(t, end_tensor), is_done, end_tensor)

# print(is_done)
# print(tf.reduce_mean(is_done))

# if tf.reduce_mean(is_done) == tf.constant(4):
#   print('simon')
# else:
#   print('nomon')