from base import Session, engine, Base
from traduccion import Traduccion

def procesar_traducciones():
    session = Session()
    traducciones = session.query(Traduccion).filter(Traduccion.procesado==False).all()
    for elemento in traducciones:
        print(elemento)
        # Aqui se recuperaria la ruta del archivo para procesarla
        # Posterior a esto se actualiza que el elemento fue procesado
        elemento.procesado = True
    # El commit puede ir aqui o en el for despues de realizar el cambio
    # Si se ponen dentro del for se hace una busqueda despues de hacer el commit
    session.commit()
    session.close()

if __name__ == "__main__":
    procesar_traducciones()