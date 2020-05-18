#from base import Session, engine, Base
#from traduccion import Traduccion

import sys
sys.path.append("..") #dirty trick
from accesodb.base import Session, engine, Base
from accesodb.traduccion import Traduccion

import time
import os
from scriptCV import ImageProcessor

BASE_DIR = "../supertt/media/proyectos"
EXTENSION = ".jpg"

def procesar_traducciones():
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

            if not os.path.exists("processed_images/" + name_image):
                ip.saveImage("processed_images/" + name_image)
    session.commit()
    session.close()

if __name__ == "__main__":
    if not os.path.exists("processed_images"):
	    os.mkdir("processed_images")

    while True:
        procesar_traducciones()
        time.sleep(1)