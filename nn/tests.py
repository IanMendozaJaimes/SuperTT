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



def get_positional_encoding_2d(height, width, d_model):
    
    pe = np.zeros((height, width, d_model))
    d_model = int(d_model / 2)
    h_vector = np.arange(height)
    w_vector = np.expand_dims(np.arange(width), axis=1)

    div_term = np.arange(d_model) // 2
    div_term = np.exp((-2*div_term / d_model) * np.log(10000.0))
    
    pe[:, :, 0:d_model:2] = np.sin((pe[:, :, 0:d_model:2] + div_term[0::2]) * w_vector)
    pe[:, :, 1:d_model:2] = np.cos((pe[:, :, 1:d_model:2] + div_term[1::2]) * w_vector)
    pe[:, :, d_model::2] = np.sin((pe[:, :, d_model::2] + div_term[0::2]) * h_vector[:, np.newaxis, np.newaxis])
    pe[:, :, d_model+1::2] = np.cos((pe[:, :, d_model+1::2] + div_term[0::2]) * h_vector[:, np.newaxis, np.newaxis])

    return pe


# a = tf.ones((2,1,3,4))
# pe = get_positional_encoding_2d(1,3,4)

# print(a+pe)

a = tf.ones((1,6,1))
print(a)

Q = tf.keras.layers.Conv1D(filters=2, kernel_size=5, padding="same", use_bias=False)

print(Q(a))



