from __future__ import absolute_import, division, print_function, unicode_literals

import numpy as np
import tensorflow as tf
from PIL import Image
import os


class Encoder(tf.keras.Model):

    def __init__(self):
        super(Encoder, self).__init__()
        
        self.pooling1 = tf.keras.layers.MaxPool2D(pool_size=(2,2), strides=(2,2), padding='same')
        self.pooling2 = tf.keras.layers.MaxPool2D(pool_size=(2,2), strides=(2,2), padding='same')
        self.pooling3 = tf.keras.layers.MaxPool2D(pool_size=(2,1), strides=(2,1), padding='same')
        self.pooling4 = tf.keras.layers.MaxPool2D(pool_size=(1,2), strides=(1,2), padding='same')

        self.conv1 = tf.keras.layers.Conv2D(64,  (3,3), (1,1), 'same', activation='relu')
        self.conv2 = tf.keras.layers.Conv2D(128, (3,3), (1,1), 'same', activation='relu')
        self.conv3 = tf.keras.layers.Conv2D(256, (3,3), (1,1), 'same')
        self.conv4 = tf.keras.layers.Conv2D(256, (3,3), (1,1), 'same', activation='relu')
        self.conv5 = tf.keras.layers.Conv2D(512, (3,3), (1,1), 'same')
        self.conv6 = tf.keras.layers.Conv2D(512, (3,3), (1,1), 'valid')

        self.batch_normalization1 = tf.keras.layers.BatchNormalization()
        self.batch_normalization2 = tf.keras.layers.BatchNormalization()
        self.batch_normalization3 = tf.keras.layers.BatchNormalization()

    def call(self, x, training=False):
        
        y = self.conv1(x)
        y = self.pooling1(y)
        y = self.conv2(y)
        y = self.pooling2(y)
        y = self.conv3(y)
        y = self.batch_normalization1(y, training)
        y = tf.nn.relu(y)
        y = self.conv4(y)
        y = self.pooling3(y)
        y = self.conv5(y)
        y = self.batch_normalization2(y, training)
        y = tf.nn.relu(y)
        y = self.pooling4(y)
        y = self.conv6(y)
        y = self.batch_normalization3(y, training)
        y = tf.nn.relu(y)

        # adding positional encodings
        y += self.get_positional_encoding_2d(y.shape[1], y.shape[2], y.shape[3])

        y = tf.reshape(y, (y.shape[0], -1, y.shape[3]))

        return y
    
    def get_positional_encoding_2d(self, height, width, d_model):
    
        positional_encodings = np.zeros((height, width, d_model))
        d_model = int(d_model / 2)
        h_vector = np.arange(height)
        w_vector = np.expand_dims(np.arange(width), axis=1)

        div_term = np.arange(d_model) // 2
        div_term = np.exp((-2*div_term / d_model) * np.log(10000.0))
        
        positional_encodings[:, :, 0:d_model:2] = np.sin((positional_encodings[:, :, 0:d_model:2] + div_term[0::2]) * w_vector)
        positional_encodings[:, :, 1:d_model:2] = np.cos((positional_encodings[:, :, 1:d_model:2] + div_term[1::2]) * w_vector)
        positional_encodings[:, :, d_model::2] = np.sin((positional_encodings[:, :, d_model::2] + div_term[0::2]) * h_vector[:, np.newaxis, np.newaxis])
        positional_encodings[:, :, d_model+1::2] = np.cos((positional_encodings[:, :, d_model+1::2] + div_term[0::2]) * h_vector[:, np.newaxis, np.newaxis])

        return positional_encodings


class InitialHidden(tf.keras.Model):

  def __init__(self, units):
    super(InitialHidden, self).__init__()
    
    self.fc = tf.keras.layers.Dense(units, activation='tanh')

  def __call__(self, x):

    x = tf.math.reduce_mean(x, axis=1)
    return self.fc(x) 


class BahdanauAttention(tf.keras.layers.Layer):
  def __init__(self, units):
    super(BahdanauAttention, self).__init__()
    
    self.W1 = tf.keras.layers.Dense(units)
    self.W2 = tf.keras.layers.Dense(units)
    self.V = tf.keras.layers.Dense(1)

  def call(self, features, hidden):
    # features(Encoder output) shape == (batch_size, L, 512)

    # hidden shape == (batch_size, hidden_size)
    # hidden_with_time_axis shape == (batch_size, 1, hidden_size)
    hidden_with_time_axis = tf.expand_dims(hidden, 1)

    # score shape == (batch_size, L, hidden_size)
    score = tf.nn.tanh(self.W1(features) + self.W2(hidden_with_time_axis))

    # attention_weights shape == (batch_size, L, 1)
    # you get 1 at the last axis because you are applying score to self.V
    attention_weights = tf.nn.softmax(self.V(score), axis=1)

    # context_vector shape after sum == (batch_size, hidden_size)
    context_vector = attention_weights * features
    context_vector = tf.reduce_sum(context_vector, axis=1)

    return context_vector, attention_weights


class Decoder(tf.keras.Model):

  def __init__(self, units, embedding_dim, vocab_size):
    super(Decoder, self).__init__()
    
    self.Wout = tf.keras.layers.Dense(vocab_size, use_bias=False)
    self.o_t = tf.keras.layers.Dense(units, use_bias=False, activation='tanh')
    self.lstm = tf.keras.layers.LSTM(units, return_state=True)
    self.embedding = tf.keras.layers.Embedding(vocab_size, embedding_dim)

    self.attention = BahdanauAttention(units)
  

  def __call__(self, features, hidden, previous_y, previous_out):

    # embedding_previous_y shape == (batch_size, embedding_dim)
    embedding_previous_y = self.embedding(previous_y)

    # previous_out shape == (batch_size, units)
    # lstm_input shape == (batch_sise, embedding_dim + units)
    lstm_input = tf.concat([embedding_previous_y, previous_out], axis=1)
    lstm_input = tf.expand_dims(lstm_input, axis=1)

    # hidden is a list of hidden state and carry state respectively
    # each element has a shape == (batch_size, units)
    # actually output and state are the same tensors
    output, state, carry = self.lstm(lstm_input, initial_state=hidden)

    # context_vector shape == (batch_size, hidden_size)
    # attention_weights == (batch_size, L, 1)
    context_vector, attention_weights = self.attention(features, state)

    # calculating the output
    out = self.o_t(tf.concat([output, context_vector], axis=1))
    output = self.Wout(out)

    return output, out, attention_weights, [state, carry]


class RecognizeMathExpressionsModel():

    def __init__(self, weights_path, vocab_path):

        self.tokens = dict()

        # this is fixed for this model
        self.UNITS = 512
        self.EMBEDDING_DIM = 80
        self.VOCAB_SIZE = 494
        self.MAX_LENGTH = 150

        self.END = self.VOCAB_SIZE - 1
        self.BEGIN = self.VOCAB_SIZE - 2

        self.encoder = Encoder()
        self.decoder = Decoder(self.UNITS, self.EMBEDDING_DIM, self.VOCAB_SIZE)
        self.initial_state = InitialHidden(self.UNITS)
        self.initial_carry = InitialHidden(self.UNITS)
        self.initial_out = InitialHidden(self.UNITS)

        self.load_weights(weights_path)
        self.load_vocab(vocab_path)


    def load_weights(self, weights_path):

        img_tensor = tf.random.uniform((1, 50, 120, 1))
        features = self.encoder(img_tensor)

        hidden = self.initial_state(features)
        carry = self.initial_carry(features)
        out = self.initial_out(features)
        dec_input = tf.constant([self.BEGIN] * 1)
            
        predictions, out, attention_weights, hidden = self.decoder(features, [hidden, carry], dec_input, out)

        self.encoder.load_weights(weights_path + '/encoder.h5')
        self.decoder.load_weights(weights_path + '/decoder.h5')
        self.initial_state.load_weights(weights_path + '/state.h5')
        self.initial_carry.load_weights(weights_path + '/carry.h5')
        self.initial_out.load_weights(weights_path + '/out.h5')


    def load_vocab(self, vocab_path):

        tokens_file = open(vocab_path, 'r')
        count = 0

        for line in tokens_file:
            count += 1
            self.tokens[count] = line.strip()

        self.tokens[count+1] = '<s>'
        self.tokens[count+2] = '</s>'

    
    def predict(self, image):

        img_tensor = image

        if len(image.shape) == 3:
            img_tensor = tf.expand_dims(image, 0)

        if len(img_tensor.shape) != 4:
            return 'error: Received a non valid image, expected a tensor of shape = (1, IMAGE_HEIGHT, IMAGE_WIDTH, 1) or shape = (IMAGE_HEIGHT, IMAGE_WIDTH, 1) received instead ' + str(image.shape)

        features = self.encoder(img_tensor, False)
        
        hidden = self.initial_state(features)
        carry = self.initial_carry(features)
        out = self.initial_out(features)
        dec_input = tf.constant([self.BEGIN] * 1)

        hidden_and_carry = [hidden, carry]

        expression = ''

        for i in range(0, self.MAX_LENGTH):
            predictions, out, attention_weights, hidden_and_carry = self.decoder(features, hidden_and_carry, dec_input, out)

            predicted_id = tf.random.categorical(predictions, 1)[0][0].numpy()

            if predicted_id == self.END:
                return expression[:-1]

            dec_input = tf.constant([predicted_id])
            expression += self.tokens[predicted_id] + ' '





