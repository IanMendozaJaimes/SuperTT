#from base import Session, engine, Base
#from traduccion import Traduccion

import sys
#sys.path.append("..") #dirty trick
from accesodb.base import Session, engine, Base
from accesodb.traduccion import Traduccion

import time
import os


from ImageAnalysis_Module.ImagePreprocessor.scriptCV import ImageProcessor
from nn.test_model import img2latSeqConverter, initModels
from MexpTokenizer.NSequenceToLatex import Converter

BASE_DIR = "supertt/media/proyectos"
EXTENSION = ".jpg"
TIME_STEP = 100 # Segundos

def procesar_traducciones(encoder, decoder, converter):
    session = Session()
    traducciones = session.query(Traduccion).filter(Traduccion.procesado==False).all()
    for elemento in traducciones:

        name_image = f"{elemento.usuario_id}/{elemento.proyecto_id}/{elemento.id}{EXTENSION}"
        print(name_image)
        #assert
        if os.path.exists(BASE_DIR + "/" + name_image):
            ip = ImageProcessor(BASE_DIR + "/" + name_image)
            ip.GaussianTransform()
            name_image = name_image.replace("/", "_").replace(EXTENSION, ".png")

            if not os.path.exists(f"{BASE_DIR}/processed_images/{name_image}" ):
                ip.saveImage(f"{BASE_DIR}/processed_images/{name_image}")
                seq = img2latSeqConverter(f"{BASE_DIR}/processed_images/{name_image}", encoder, decoder)
                
                elemento.traduccion = converter.seq2Lat(seq)
                elemento.procesado = True

    session.commit()
    session.close()

if __name__ == "__main__":
    if not os.path.exists(f"{BASE_DIR}/processed_images"):
	    os.mkdir(f"{BASE_DIR}/processed_images")
    encoder, decoder = initModels("nn")
    c = Converter()

    while True:
        procesar_traducciones(encoder, decoder, c)
        time.sleep(TIME_STEP)
