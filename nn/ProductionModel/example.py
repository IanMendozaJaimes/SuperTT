from model import RecognizeMathExpressionsModel

import tensorflow as tf

import matplotlib.pyplot as plt


def load_image(image_path, resize=False):
    
    img = tf.io.read_file(image_path)
    img = tf.image.decode_png(img, channels=1)
    img = tf.image.convert_image_dtype(img, tf.float32)

    if resize:
      img = tf.image.resize(img, (150, 300))

    return img

WEIGHTS_PATH = './ModelWeights'
VOCAB_PATH = './vocab.txt'

re = RecognizeMathExpressionsModel(WEIGHTS_PATH, VOCAB_PATH)

image = load_image('./1_12_130.png', True)

print(re.predict(image))
print('')

plt.imshow(tf.squeeze(image).numpy(), cmap='gray')
plt.show()


