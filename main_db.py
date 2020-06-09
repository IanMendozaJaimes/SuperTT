import time
import os
import sys

from accesodb.base import Session, engine, Base
from accesodb.traduccion import Traduccion

from ImageAnalysis_Module.ImagePreprocessor.scriptCV import ImageProcessor, ImageAlgorithm
#from nn.test_model import img2latSeqConverter, initModels
#from MexpTokenizer.NSequenceToLatex import Converter
from nn.ProductionModel.imageloader import ImagePredict

BASE_DIR = "supertt/media/proyectos"
EXTENSION = ".jpg"
TIME_STEP = 10 # Segundos

def procesar_traducciones(pred):
    session = Session()
    traducciones = session.query(Traduccion).filter(Traduccion.procesado==False).all()
    for elemento in traducciones:

        name_image = f"{elemento.usuario_id}/{elemento.proyecto_id}/{elemento.id}{EXTENSION}"
        print(name_image)
        #assert
        if os.path.exists(BASE_DIR + "/" + name_image):
            ip = ImageProcessor(BASE_DIR + "/" + name_image)
            ip.processBinarization(algorithm=ImageAlgorithm.OTSU)
            name_image = name_image.replace("/", "_").replace(EXTENSION, ".png")

            if not os.path.exists(f"{BASE_DIR}/processed_images/{name_image}" ):
                ip.saveImage(f"{BASE_DIR}/processed_images/{name_image}", algorithm=ImageAlgorithm.OTSU)
                #seq = img2latSeqConverter(f"{BASE_DIR}/processed_images/{name_image}", encoder, decoder)
                pred.load_image(f"{BASE_DIR}/processed_images/{name_image}")
                seq = pred.predict()
                
                elemento.traduccion = seq
                elemento.procesado = True

    session.commit()
    session.close()

if __name__ == "__main__":
    if not os.path.exists(f"{BASE_DIR}/processed_images"):
	    os.mkdir(f"{BASE_DIR}/processed_images")
    #encoder, decoder = initModels("nn")
    pred = ImagePredict()
    while True:
        procesar_traducciones(pred)
        time.sleep(TIME_STEP)
