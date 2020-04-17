from __future__ import absolute_import, division, print_function, unicode_literals

import tensorflow as tf

from model import *

print('=======================================')
print('==============  TESTING  ==============')
print('=======================================')

num_filters = 5
kernel_shape = (3,3)
training = True
batch_size = 2
w = 5
h = 5

image = tf.ones((batch_size,w,h,1))
# print(image)

# encoder = Encoder()

# y = encoder(image, training)
# print(y[0])

test_conv1 = tf.keras.layers.Conv2D(filters=num_filters, kernel_size=kernel_shape, strides=(1,1))

y = test_conv1(image)

L = y.shape[1] * y.shape[2]

y = tf.reshape(y, (batch_size, L, num_filters))

print(y)

# ATTENTION MECHANISM

attention_dim = 3
Ua = tf.keras.layers.Dense(attention_dim)
UaY = Ua(y)
# UaY shape = (batch_size, L, attention_dim)
print(UaY)

print(Ua.weights)

ht = tf.constant([[1., 2., 3., 4., 5., 6.], [1., 2., 3., 4., 5., 6.]])
# ht shape = (batch_size, 1, units)
ht = tf.expand_dims(ht,1)

Wa = tf.keras.layers.Dense(attention_dim)

print('===================================')
print('===================================')
print('H(t-1):',ht)

WaH = Wa(ht)
print(WaH)

print(Wa.weights)

print('===================================')
print('LOOK THE SUM:')
# z = tf.math.add(WaH,UaY)
z = WaH + UaY
z = tf.nn.tanh(z)

V = tf.keras.layers.Dense(1)

print(z)

print('===================================')
print('LOOK AT THE V:')
print(V)

VZ = V(z)

print(V.weights)

print('===================================')
print('LOOK AT THE VZ:')
print(VZ)

a = tf.reshape(VZ, (batch_size, VZ.shape[2], VZ.shape[1]))
print('A:',a)
a = tf.nn.softmax(a)
a = tf.reshape(a, (batch_size, a.shape[2], a.shape[1]))

print('a:', a)

print('===================================')
print('CONVOLUTION FEATURES:')

aa = a * .3

print(aa)

Q = tf.keras.layers.Conv1D(filters=10, kernel_size=200, padding="same", use_bias=False)
F = Q(aa)

print('Q(aa):')
print(F)
print(Q.weights)

Uf = tf.keras.layers.Dense(attention_dim)
UfF = Uf(F)

print('UfF:',UfF)

print('===================================')
print('RNN')

# ht shape = (batch_size, units)
# context_vector = (batch_size, D)
# y_values = (batch_size,)

vocab_size = 10
embedding_dim = 6
E = tf.keras.layers.Embedding(vocab_size, embedding_dim)

y_values = tf.constant([[1., 2., 0.], [1., 2., 4.]])

print(y_values[:,2])

EYV = E(y_values[:,0])

# Ey shape = (batch_size, embedding_dim)
print(EYV)

units = 7
Wyz = tf.keras.layers.Dense(units)

print(Wyz(EYV))

print(Wyz.weights)

h = tf.constant([[1., 2., 3., 4., 5., 6., 7.], [1., 2., 3., 4., 5., 6., 7.]])
Uhz = tf.keras.layers.Dense(units)

print('h:', h)

print(Uhz(h))

print(Uhz.weights)

context_vector = tf.constant([[1., 2., 3.], [1., 2., 3.]])
Ccz = tf.keras.layers.Dense(units)

print('context_vector:', context_vector)

print(Ccz(context_vector))

print(Ccz.weights)

# batch_normalization1 = tf.keras.layers.BatchNormalization()
# pooling1 = tf.keras.layers.MaxPooling2D(pool_size=(2,2), strides=(2,2))

# test_conv2 = tf.keras.layers.Conv2D(filters=num_filters+1, kernel_size=kernel_shape, strides=(1,1))
# batch_normalization2 = tf.keras.layers.BatchNormalization()

# test_conv3 = tf.keras.layers.Conv2D(filters=num_filters+2, kernel_size=kernel_shape, strides=(1,1))
# batch_normalization3 = tf.keras.layers.BatchNormalization()

# test_conv4 = tf.keras.layers.Conv2D(filters=num_filters+3, kernel_size=kernel_shape, strides=(1,1))
# batch_normalization4 = tf.keras.layers.BatchNormalization()
# dropout = tf.keras.layers.Dropout(0.2)

# y = test_conv1(image)
# y = batch_normalization1(y, training=training)
# y = tf.nn.relu(y)

# y = pooling1(y)

# y = test_conv2(y)
# y = batch_normalization2(y, training=training)
# y = tf.nn.relu(y)

# y = test_conv3(y)
# y = batch_normalization3(y, training=training)
# y = tf.nn.relu(y)

# y = test_conv4(y)
# y = batch_normalization4(y, training=training)
# y = dropout(y, training=training)
# y = tf.nn.relu(y)

# print(y)





