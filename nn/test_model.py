from __future__ import absolute_import, division, print_function, unicode_literals

import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt

from model import *

BATCH_SIZE = 2
UNITS = 128
EMBEDDING_DIM = 128
VOCAB_SIZE = 111
ATTENTION_DIM = 128
K = 10
Q_WIDTH = 100
END = VOCAB_SIZE + 1
begin = tf.constant(1000)

IMG_HEIGHT = 480
IMG_WIDTH = 640


def load_image(image_path):
    img = tf.io.read_file(image_path)
    img = tf.image.decode_jpeg(img, channels=1)
    img = tf.image.convert_image_dtype(img, tf.float32)
    img = tf.image.resize(img, (IMG_HEIGHT, IMG_WIDTH))
    return img


def get_one_hot_predict(value, vocab_size):
    return tf.one_hot(tf.constant([value]), vocab_size, on_value=1.0, off_value=0.0)


def get_one_hot(values, vocab_size):
    if values[0] == begin:
        begin_tensor = tf.constant([END - 1] * values.shape[0])
        return tf.one_hot(begin_tensor, vocab_size, on_value=1.0, off_value=0.0) 

    values = values - 1
    end_tensor = tf.constant([END] * values.shape[0])
    values = tf.where(tf.less_equal(values, end_tensor), values, end_tensor)

    return tf.one_hot(values, vocab_size, on_value=1.0, off_value=0.0)


def sort_aux(candidate):
    return -candidate[0]



def predict_greedy(encoder, decoder, image, plot_attention=False):

    sequence = list()
    attention_plot = list()
    result = list()

    print('greedy')

    features = encoder(image, training=False)
    features = features * 0.3

    B = tf.zeros((1, features.shape[1], 1))
    hidden = decoder.reset_state(batch_size=features.shape[0])
    dec_input = get_one_hot(tf.zeros((features.shape[0]), dtype=tf.dtypes.int32), VOCAB_SIZE + 2)

    output, ht, attention_weights = decoder([dec_input, hidden, features, B])

    while True:
        dec_input = np.argmax(output.numpy())
        sequence.append(dec_input)

        if plot_attention:
            attention_plot.append(attention_weights)
            result.append(dec_input)

        if dec_input == END:

            if attention_plot:
                fig = plt.figure(figsize=(10, 10))

                len_result = len(result)
                for l in range(len_result):
                    temp_att = attention_plot[l][0]
                    # temp_att = tf.reshape(attention_plot[l][0], (22, 32)).numpy()
                    temp_att = np.resize(attention_plot[l][0], (8,8))
                    print(temp_att)
                    ax = fig.add_subplot(len_result//2, len_result//2, l+1)
                    ax.set_title(result[l])
                    img = ax.imshow(tf.squeeze(image[0]))
                    ax.imshow(temp_att, cmap='gray', alpha=0.6, extent=img.get_extent())

                plt.tight_layout()
                #plt.show()

            return sequence

        B = B + attention_weights
        hidden = ht
        dec_input = get_one_hot_predict(dec_input, VOCAB_SIZE + 2)

        output, ht, attention_weights = decoder([dec_input, hidden, features, B])



def predict(encoder, decoder, image, beam_dim):
    
    features = encoder(image, training=False)
    features = features * 0.4

    B = tf.zeros((1, features.shape[1], 1))
    hidden = decoder.reset_state(batch_size=features.shape[0])
    dec_input = get_one_hot(tf.zeros((features.shape[0]), dtype=tf.dtypes.int32), VOCAB_SIZE + 2)

    output, ht, attention_weights = decoder([dec_input, hidden, features, B])

    B = B + attention_weights

    beam_candidates = tf.math.top_k(output, k=beam_dim)
    beam_outputs = list()
    beam_hts = list()
    beam_bs = list()
    sequences = list()
    probabilities = list()
    end_symbol_appears = False

    candidates = list()
    candidates_positions = list()
    candidates_probabilities = list()

    beam_positions = beam_candidates[1].numpy()
    beam_probabilites = beam_candidates[0].numpy()

    vocab_s = VOCAB_SIZE + 2

    for x in range(0, beam_dim):
        beam_outputs.append(get_one_hot_predict(beam_positions[0][x], vocab_s))
        beam_hts.append(ht)
        beam_bs.append(B)
        sequences.append([beam_positions[0][x]])
        probabilities.append(beam_probabilites[0][x])
        candidates.append(None)
        candidates_positions.append(None)
        candidates_probabilities.append(None)

    while True:
        print('new option')
        possible_winners = list()
        for x in range(0, beam_dim):
            output, ht, attention_weights = decoder([beam_outputs[x], beam_hts[x], features, beam_bs[x]])
            beam_bs[x] = beam_bs[x] + attention_weights
            candidates[x] = (ht, list(beam_bs[x]))

            beam_candidates = tf.math.top_k(output, k=beam_dim)
            candidates_positions[x] = (beam_candidates[1].numpy())[0]
            candidates_probabilities[x] = (beam_candidates[0].numpy())[0]
            candidates_probabilities[x] *= probabilities[x]
            
            for y in range(0, beam_dim):
                possible_winners.append((candidates_probabilities[x][y], candidates_positions[x][y], x)) 

        possible_winners = sorted(possible_winners, key=sort_aux)

        for x in range(0, beam_dim):
            beam_outputs[x] = get_one_hot_predict(possible_winners[x][1], vocab_s)
            beam_hts[x] = candidates[possible_winners[x][2]][0]
            beam_bs[x] = candidates[possible_winners[x][2]][1]
            probabilities[x] = possible_winners[x][0]
            sequences[x].append(possible_winners[x][1])

            if possible_winners[x][1] == END:
                end_symbol_appears = True

        if end_symbol_appears:
            best = 0
            count = 0
            i = 0
            for p in probabilities:
                if best < p:
                    best = p
                    i = count
                count += 1
            return sequences[i], probabilities[i]

            



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

########################## DO NOT DELETE THIS CHUNK OF CODE ###########
#automatic script
BASE_DIR = "../Integration_scripts/images"
import time
import subprocess
import os
import sys
while True:

    #image_url = './exampleImages/001-equation000.i.png'


    if len( os.listdir(BASE_DIR) ) > 0: #if there is a file in BASE_DIR
        
        image_url = BASE_DIR + "/" + os.listdir(BASE_DIR)[0]
        print(image_url)

        temp_input = tf.expand_dims(load_image(image_url), 0)
        
        # plt.imshow(tf.squeeze(temp_input).numpy(), cmap='gray')
        # plt.show()
        #prediction = predict(encoder, decoder, temp_input, 10)

        prediction = predict_greedy(encoder, decoder, temp_input, True)
        print(prediction)
        

        print("type: "+str(type(prediction)))
        prediction2 = [str(e) for e in prediction]
        subprocess.call(["python", "../MexpTokenizer/main_seq2lat.py", ",".join(prediction2), os.listdir(BASE_DIR)[0].split(".")[0], sys.argv[1]])

        os.remove(image_url) #once finished, delete image
    time.sleep(1)
######################################################3
    # import pathlib
    # image_url = './exampleImages/4.png'
    # data_dir = tf.keras.utils.get_file(origin=image_url, fname='math_expressions', untar=False)
    # data_dir = pathlib.Path(data_dir)



