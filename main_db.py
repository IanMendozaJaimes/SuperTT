import time
import os
import sys

from accesodb.base import Session, engine, Base
from accesodb.traduccion import Traduccion

from ImageAnalysis_Module.ImagePreprocessor.scriptCV import ImageProcessor, ImageAlgorithm
from nn.ProductionModel.imageloader import ImagePredict

BASE_DIR = "supertt/media/proyectos"
EXTENSION = ".jpg"
TIME_STEP = 10 # Segundos


IMAGE_TEST_DATASET = "/home/qapolo/Escritorio/SuperTT/supertt/media/proyectos/processed_images/1_10_98.png"
def procesar_traducciones(pred):
    session = Session()
    traducciones = session.query(Traduccion).filter(Traduccion.procesado==False).all()
    for elemento in traducciones:

        name_image = f"{elemento.usuario_id}/{elemento.proyecto_id}/{elemento.id}{EXTENSION}"
        print(name_image)
        #assert
        if os.path.exists(BASE_DIR + "/" + name_image):
            ip = ImageProcessor(BASE_DIR + "/" + name_image)
            ip.processBinarization(algorithm=ImageAlgorithm.SAUVOLA)
            name_image = name_image.replace("/", "_").replace(EXTENSION, ".png")

            if not os.path.exists(f"{BASE_DIR}/processed_images/{name_image}" ):
                ip.saveImage(f"{BASE_DIR}/processed_images/{name_image}", algorithm=ImageAlgorithm.SAUVOLA,res=False)                
                pred.load_image(f"{BASE_DIR}/processed_images/{name_image}", True)
                #pred.load_image(IMAGE_TEST_DATASET, True) #HARDCODED
                elemento.traduccion = pred.predict()
                elemento.procesado = True

    session.commit()
    session.close()

if __name__ == "__main__":
    if not os.path.exists(f"{BASE_DIR}/processed_images"):
	    os.mkdir(f"{BASE_DIR}/processed_images")

    pred = ImagePredict()
    while True:
        procesar_traducciones(pred)
        time.sleep(TIME_STEP)
