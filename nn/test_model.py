from __future__ import absolute_import, division, print_function, unicode_literals

import tensorflow as tf
import numpy as np

from model import *

BATCH_SIZE = 2
UNITS = 256
EMBEDDING_DIM = 256
VOCAB_SIZE = 117
ATTENTION_DIM = 256
K = 10
Q_WIDTH = 200
END = VOCAB_SIZE + 1
begin = tf.constant(1000)


def get_one_hot(values, vocab_size):
    if values[0] == begin:
        begin_tensor = tf.constant([END - 1] * values.shape[0])
        return tf.one_hot(begin_tensor, vocab_size, on_value=1.0, off_value=0.0) 

    values = values - 1
    end_tensor = tf.constant([END] * values.shape[0])
    values = tf.where(tf.less_equal(values, end_tensor), values, end_tensor)

    return tf.one_hot(values, vocab_size, on_value=1.0, off_value=0.0)


# def predict(encoder, decoder, image):
    
#     features = encoder(image, training=False)
#     B = tf.zeros((1, features.shape[1], 1))
#     hidden = decoder.reset_state(batch_size=features.shape[0])
#     dec_input = get_one_hot(tf.zeros((features.shape[0]), dtype=tf.dtypes.int32), VOCAB_SIZE + 2)

#     while True:





# init the models
encoder = Encoder(name='ENCODER')
decoder = Decoder(UNITS, EMBEDDING_DIM, VOCAB_SIZE + 2, ATTENTION_DIM, K, Q_WIDTH)

# we need to use the models first, in order to init its weights
image = tf.random.uniform((1, 300, 300, 1))
B = tf.zeros((1, 121, 1))
hidden = decoder.reset_state(batch_size=1)
dec_input = get_one_hot(tf.zeros((1), dtype=tf.dtypes.int32), VOCAB_SIZE + 2)

features = encoder(image)
output, ht, attention_weights = decoder([dec_input, hidden, features, B])

# then we can load the real weights
encoder.load_weights('SavedModels/model_encoder.h5')
decoder.load_weights('SavedModels/model_decoder.h5')

print(decoder.weights)


