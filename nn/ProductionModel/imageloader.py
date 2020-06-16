from nn.ProductionModel.model import RecognizeMathExpressionsModel
import tensorflow as tf
import matplotlib.pyplot as plt

BASE_DIR = "./nn/ProductionModel"
WEIGHTS_PATH = BASE_DIR + "/" + 'ModelWeights'
VOCAB_PATH = BASE_DIR + "/" +'vocab.txt'

class ImagePredict:
    def load_image(self, image_path, resize=False):
        
        self.img = tf.io.read_file(image_path)
        self.img = tf.image.decode_png(self.img, channels=1)
        self.img = tf.image.convert_image_dtype(self.img, tf.float32)

        #plt.imshow(tf.squeeze(self.img).numpy(), cmap='gray')
        #plt.show()



        if resize:
            self.img = tf.image.resize(self.img, (150, 300))
        #return img
    def __init__(self):
        self.re = RecognizeMathExpressionsModel(WEIGHTS_PATH, VOCAB_PATH)
    def predict(self):
        return self.re.predict(self.img)


