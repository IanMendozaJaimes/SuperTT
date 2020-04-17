from __future__ import absolute_import, division, print_function, unicode_literals

import tensorflow as tf


class FCNN_last(tf.keras.Model):

	def __init__(self, num_filters, kernel_size, dropout_factor):
		super(FCNN_last, self).__init__()

		self.conv = tf.keras.layers.Conv2D(filters=num_filters, kernel_size=kernel_size, strides=(1,1), use_bias=False)
		self.batch_normalization = tf.keras.layers.BatchNormalization()
		self.dropout = tf.keras.layers.Dropout(dropout_factor)
		# self.dropout_factor = dropout_factor

	def __call__(self, x, training):

		y = self.conv(x)
		y = self.batch_normalization(inputs=y, training=training)
		y = self.dropout(inputs=y, training=training)

		# if not training:
		# 	y = self.dropout_factor * y

		y = tf.nn.relu(y)

		return y


class FCNN(tf.keras.Model):

	def __init__(self, num_filters, kernel_size):
		super(FCNN, self).__init__()

		self.conv = tf.keras.layers.Conv2D(filters=num_filters, kernel_size=kernel_size, strides=(1,1), use_bias=False)
		self.batch_normalization = tf.keras.layers.BatchNormalization()


	def __call__(self, x, training):

		y = self.conv(x)
		y = self.batch_normalization(inputs=y, training=training)
		y = tf.nn.relu(y)

		return y


class CNN_block_last(tf.keras.Model):

	def __init__(self, num_filters, kernel_size, dropout_factor):
		super(CNN_block_last, self).__init__()

		self.conv1 = FCNN_last(num_filters, kernel_size, dropout_factor)
		self.conv2 = FCNN_last(num_filters, kernel_size, dropout_factor)
		self.conv3 = FCNN_last(num_filters, kernel_size, dropout_factor)
		self.conv4 = FCNN_last(num_filters, kernel_size, dropout_factor)

		self.pooling = tf.keras.layers.MaxPool2D(pool_size=(2,2), strides=(2,2))

	def __call__(self, x, training):

		y = self.conv1(x, training)
		y = self.conv2(y, training)
		y = self.conv3(y, training)
		y = self.conv4(y, training)
		y = self.pooling(y)

		return y

class CNN_block(tf.keras.Model):

	def __init__(self, num_filters, kernel_size):
		super(CNN_block, self).__init__()

		self.conv1 = FCNN(num_filters, kernel_size)
		self.conv2 = FCNN(num_filters, kernel_size)
		self.conv3 = FCNN(num_filters, kernel_size)
		self.conv4 = FCNN(num_filters, kernel_size)

		self.pooling = tf.keras.layers.MaxPool2D(pool_size=(2,2), strides=(2,2))


	def __call__(self, x, training):

		y = self.conv1(x, training)
		y = self.conv2(y, training)
		y = self.conv3(y, training)
		y = self.conv4(y, training)
		y = self.pooling(y)

		return y



class Encoder(tf.keras.Model):

	def __init__(self, name=None):
		super(Encoder, self).__init__(name=name)

		self.block1 = CNN_block(32, (3,3))
		self.block2 = CNN_block(64, (3,3))
		self.block3 = CNN_block(64, (3,3))
		self.block4 = CNN_block_last(128, (3,3), 0.2)


	def __call__(self, x, training=False):

		y = self.block1(x, training)
		y = self.block2(y, training)
		y = self.block3(y, training)
		y = self.block4(y, training)
		
		y = tf.reshape(y, (y.shape[0], y.shape[1] * y.shape[2], y.shape[3]))
		# y shape = (batch_size, L, D)
		# L = W x H
		# D : size of context vector

		return y


# n 	: the size of the hidden state h(t). n = 256 in the article
# D 	: the ai dimension 
# L 	: number of features = WxH
# na 	: the attention dimension
# m		: embedding dimension. m = 256 in the article
	
class AttentionMLP(tf.keras.Model):

	def __init__(self, attention_dim, k, q_width):
		super(AttentionMLP, self).__init__()

		self.Wa = tf.keras.layers.Dense(attention_dim, use_bias=False)
		self.Ua = tf.keras.layers.Dense(attention_dim, use_bias=False)
		self.Va = tf.keras.layers.Dense(1, use_bias=False)
		self.Uf = tf.keras.layers.Dense(attention_dim, use_bias=False)
		self.Q = tf.keras.layers.Conv1D(filters=k, kernel_size=q_width, padding="same", use_bias=False)
		


	def __call__(self, features, previous_hidden_state, B):
		# previous_hidden_state = h(t-1), shape = (batch_size, hidden_size)
		# previous_hidden_reshaped shape = (batch_size, 1, hidden_size)
		previous_hidden_reshaped = tf.expand_dims(previous_hidden_state, 1)

		# B shape = (batch_size, L, 1)
		# F shape = (batch_size, L, k)
		F = self.Q(B)

		# e shape = (batch_size, L, 1)
		e = self.Wa(previous_hidden_reshaped) + self.Ua(features) + self.Uf(F)
		e = tf.nn.tanh(e)
		e = self.Va(e)

		# a shape = (batch_size, L, 1)
		attention_weights = tf.reshape(e, (e.shape[0], e.shape[2], e.shape[1]))
		attention_weights = tf.nn.softmax(attention_weights)
		attention_weights = tf.reshape(attention_weights, (attention_weights.shape[0], attention_weights.shape[2], attention_weights.shape[1]))

		# features shape = (batch_size, L, D)
		context_vector = attention_weights * features
		context_vector = tf.reduce_sum(context_vector, axis=1)

		return context_vector, attention_weights



class UpdateGate(tf.keras.Model):
	
	def __init__(self, units):
		super(UpdateGate, self).__init__()
		
		self.Wyz = tf.keras.layers.Dense(units, use_bias=False)
		self.Uhz = tf.keras.layers.Dense(units, use_bias=False)

	def __call__(self, Ey, previous_hidden_state, ccz):

		z = self.Wyz(Ey) + self.Uhz(previous_hidden_state) + ccz
		z = tf.nn.sigmoid(z)
		
		return z


class ResetGate(tf.keras.Model):

	def __init__(self, units):
		super(ResetGate, self).__init__()

		self.Wyr = tf.keras.layers.Dense(units, use_bias=False)
		self.Uhr = tf.keras.layers.Dense(units, use_bias=False)
		self.Ccr = tf.keras.layers.Dense(units, use_bias=False)

	def __call__(self, Ey, previous_hidden_state, context_vector):

		r = self.Wyr(Ey) + self.Uhr(previous_hidden_state) + self.Ccr(context_vector)
		r = tf.nn.sigmoid(r)

		return r


class CandidateActivation(tf.keras.Model):

	def __init__(self, units):
		super(CandidateActivation, self).__init__()

		self.Wyh = tf.keras.layers.Dense(units)
		self.Urh = tf.keras.layers.Dense(units)

	def __call__(self, Ey, reset_gate, previous_hidden_state, ccz):

		hca = self.Wyh(Ey) + self.Urh(tf.multiply(reset_gate, previous_hidden_state)) + ccz
		hca = tf.nn.tanh(hca)

		return hca


class Decoder(tf.keras.Model):

	def __init__(self, units, embedding_dim, vocab_size, attention_dim, k, q_width):
		super(Decoder, self).__init__()
		
		self.units = units
		self.attention_dim = attention_dim
		self.embedding_dim = embedding_dim
		self.E = tf.keras.layers.Dense(embedding_dim, use_bias=False) # embedding matrix
		self.z = UpdateGate(units)
		self.r = ResetGate(units)
		self.hca = CandidateActivation(units)
		self.Ccz = tf.keras.layers.Dense(units, use_bias=False)
		self.Wo = tf.keras.layers.Dense(vocab_size, use_bias=False)
		self.Wh = tf.keras.layers.Dense(embedding_dim, use_bias=False)
		self.Wc = tf.keras.layers.Dense(embedding_dim, use_bias=False)

		self.attention = AttentionMLP(attention_dim, k, q_width)


	def __call__(self, inputs):

		previous_target = inputs[0]
		previous_hidden_state = inputs[1]
		features = inputs[2]
		B = inputs[3]
		
		# attention mechanism
		# context_vector shape = (batch_size, D)
		# attention_weights shape = (batch_size, L, 1)
		context_vector, attention_weights = self.attention(features, previous_hidden_state, B)
	
		# print('context vector:', context_vector)
		# print('attention weights:', attention_weights)

		# previous_target shape = (batch_size,)
		# Ey shape = (batch_size, embedding_dim)
		Ey = self.E(previous_target)
		# print('previous_target:', previous_target)
		# print('Ey:', Ey)

		# ccz shape = (batch_size, units)
		ccz = self.Ccz(context_vector)
		# print('ccz:', ccz)

		# zt shape = (batch_size, units)
		zt = self.z(Ey, previous_hidden_state, ccz)
		# print('zt:', zt)

		# rt shape = (batch_size, units)
		rt = self.r(Ey, previous_hidden_state, context_vector)
		# print('rt:', rt)

		# h_candidate shape = (batch_size, units)
		h_candidate = self.hca(Ey, rt, previous_hidden_state, ccz)
		# print('hca:', h_candidate)

		# ht shape = (batch_size, units)
		ht = tf.multiply((1 - zt), previous_hidden_state) + tf.multiply(zt, h_candidate)
		# print('ht:', ht)

		output = Ey + self.Wh(ht) + self.Wc(context_vector)
		output = self.Wo(output)
		output = tf.nn.softmax(output)

		return output, ht, attention_weights


	def reset_state(self, batch_size):
		return tf.zeros((batch_size, self.units))


	def reset_B(self, batch_size, L):
		return tf.zeros((batch_size, L, 1))