from __future__ import absolute_import, division, print_function, unicode_literals

import tensorflow as tf


class FCNN_last(tf.keras.Model):

	def __init__(self, num_filters, kernel_shape):
	 	super(FCNN_last, self).__init__()

		self.conv = tf.keras.layers.Conv2D(filters=num_filters, kernel_shape=kernel_shape, strides=(1,1))
		self.batch_normalization = tf.keras.layers.BatchNormalization()
		self.dropout = tf.keras.layers.Dropout(0.2)

	def __call__(self, x, training):

		y = self.conv(x)
		y = self.batch_normalization(y, training=training)
		y = self.dropout(y)
		y = tf.nn.relu(y)

		return y


class FCNN(tf.keras.Model):

	def __init__(self, num_filters, kernel_shape):
	 	super(FCNN, self).__init__()

		self.conv = tf.keras.layers.Conv2D(filters=num_filters, kernel_shape=kernel_shape, strides=(1,1))
		self.batch_normalization = tf.keras.layers.BatchNormalization()


	def __call__(self, x, training):

		y = self.conv(x)
		y = self.batch_normalization(y, training=training)
		y = tf.nn.relu(y)

		return y


class CNN_block_last(tf.keras.Model):

	def __init__(self, num_filters, kernel_shape):
		super(CNN_block_last, self).__init__()

		self.conv1 = FCNN_last(num_filters, kernel_shape)
		self.conv2 = FCNN_last(num_filters, kernel_shape)
		self.conv3 = FCNN_last(num_filters, kernel_shape)
		self.conv4 = FCNN_last(num_filters, kernel_shape)

		self.pooling = tf.keras.layers.MaxPool2D(pool_size=(2,2), strides=(2,2))

	def __call__(self, x):

		y = self.conv1(x)
		y = self.conv2(y)
		y = self.conv3(y)
		y = self.conv4(y)
		y = self.pooling(y)

		return y

class CNN_block(tf.keras.Model):

	def __init__(self, num_filters, kernel_shape):
		super(CNN_block, self).__init__()

		self.conv1 = FCNN(num_filters, kernel_shape)
		self.conv2 = FCNN(num_filters, kernel_shape)
		self.conv3 = FCNN(num_filters, kernel_shape)
		self.conv4 = FCNN(num_filters, kernel_shape)

		self.pooling = tf.keras.layers.MaxPool2D(pool_size=(2,2), strides=(2,2))


	def __call__(self, x):

		y = self.conv1(x)
		y = self.conv2(y)
		y = self.conv3(y)
		y = self.conv4(y)
		y = self.pooling(y)

		return y



class Encoder(tf.keras.Model):

	def __init__(self):
		super(Encoder, self).__init__()

		self.block1 = CNN_block(32, (3,3))
		self.block2 = CNN_block(64, (3,3))
		self.block3 = CNN_block(64, (3,3))
		self.block4 = CNN_block_last(128, (3,3))


	def __call__(self, x):

		y = self.block1(x)
		y = self.block2(y)
		y = self.block3(y)
		y = self.block4(y)

		return y


# n 	: the size of the hidden state h(t). n = 256 in the article
# D 	: the ai dimension 
# L 	: number of features = WxH
# na 	: the attention dimension
# m		: embedding dimension. m = 256 in the article
	
class AttentionMLP(tf.keras.Model):

	def __init__(self, units):
		super(AttentionMLP, self).__init__()

		self.Wa = tf.keras.layers.Dense(units)
		self.Ua = tf.keras.layers.Dense(units)
		self.Va = tf.keras.layers.Dense(1)

		# A perceptron is missed, because I still don't understand it jejetl


	def __call__(self, features, hidden):
		# hidden = h(t-1), shape = (batch_size, hidden_size)
		# hidden_reshaped shape = (batch_size, 1, hidden_size)
		hidden_reshaped = tf.expand_dims(hidden, 1)

		# features shape = (batch_size, L, D)

		# get the attention vector
		attention = tf.nn.tanh(self.Wa(hidden_reshaped) + self.Ua(features))
		attention_weights = tf.nn.softmax(self.Va(attention), axis=1)

		context_vector = attention_weights * features
		context_vector = tf.reduce_sum(context_vector, axis=1)

		return context_vector, attention_weights



class UpdateGate(tf.keras.Model):
	
	def __init__(self, units):
		super(UpdateGate, self).__init__()
		
		self.Wyz = tf.keras.layers.Dense(units)
		self.Uhz = tf.keras.layers.Dense(units)
		self.Ccz = tf.keras.layers.Dense(units)

	def __call__(self, Ey, hidden_state, context_vector):

		z = self.Wyz(Ey) + self.Uhz(hidden_state) + self.Ccz(context_vector)
		z = tf.nn.sigmoid(z)
		
		return z


class ResetGate(tf.keras.Model):

	def __init__(self, units):
		super(ResetGate, self).__init__()

		self.Wyr = tf.keras.layers.Dense(units)
		self.Uhr = tf.keras.layers.Dense(units)
		self.Ccr = tf.keras.layers.Dense(units)

	def __call__(self, Ey, hidden_state, context_vector):

		r = self.Wyr(Ey) + self.Uhr(hidden_state) + self.Ccr(context_vector)
		r = tf.nn.sigmoid(r)

		return r


class CandidateActivation(tf.keras.Model):

	def __init__(self, units):
		super(CandidateActivation, self).__init__()

		self.Wyh = tf.keras.layers.Dense(units)
		self.Urh = tf.keras.layers.Dense(units)
		self.Ccz = tf.keras.layers.Dense(units)

	def __call__(self, Ey, reset_gate, hidden_state, context_vector):

		hca = self.Wyh(Ey) + self.Urh(tf.multiply(reset_gate, hidden_state)) + self.Ccz(context_vector)
		hca = tf.nn.tanh(hca)

		return hca


class Decoder(tf.keras.Model):

	def __init__(self, units, embedding_dim, vocab_size):
		super(Decoder, self).__init__()
		
		self.units = units
		self.embedding_dim = embedding_dim
		self.E = tf.keras.layers.Embedding(vocab_size, embedding_dim) # embedding matrix
		self.z = UpdateGate(units)
		self.r = ResetGate(units)
		self.hca = CandidateActivation(units)
		self.Wo = tf.keras.layers.Dense(vocab_size)
		self.Wh = tf.keras.layers.Dense(embedding_dim)
		self.Wc = tf.keras.layers.Dense(embedding_dim)

	def __call__(self, y, hidden_state, context_vector):

		# y shape = (batch_size, 1)
		# Ey shape = (batch_size, 1, embedding_dim)
		Ey = self.E(y)

		# zt shape = (batch_size, units)
		zt = self.z(Ey, hidden_state, context_vector)

		# rt shape = (batch_size, units)
		rt = self.r(Ey, hidden_state, context_vector)

		# h_candidate shape = (batch_size, units)
		h_candidate = self.hca(Ey, rt, hidden_state, context_vector)

		# ht shape = (batch_size, units)
		ht = tf.multiply((1 - zt), hidden_state) + tf.multiply(zt, h_candidate)

		output = Ey + self.Wh(ht) + self.Wc(context_vector)
		output = self.Wo(output)
		output = tf.nn.softmax(output)

		return output, ht

	def reset_state(self, batch_size):
		return tf.zeros((batch_size, self.units))

		

# embedding_layer = tf.keras.layers.Embedding(5, 3)
# test_vector = tf.constant([[1, 2],[2, 2]])

# embedding_vector = embedding_layer(test_vector)

# print(test_vector)
# print(embedding_layer)
# print(embedding_vector)



