from NSequenceToLatex import*
import sys
sys.path.append("..") #dirty trick
from accesodb.base import Session, engine, Base
from accesodb.traduccion import Traduccion

if __name__ == '__main__':

    c = Converter()
    seq = sys.argv[1].split(",")
    seq = [int(e) for e in seq]
    translation = c.seq2Lat(seq)
    print(translation)

    #split idusuario_idproy_idtrad
    usr_id, proy_id, trad_id = sys.argv[2].split("_")

    session = Session()

    traducciones = session.query(Traduccion).filter(Traduccion.usuario_id== int(usr_id) ).all()
    traducciones = session.query(Traduccion).filter(Traduccion.proyecto_id== int(proy_id) ).all()
    traducciones = session.query(Traduccion).filter(Traduccion.id== int(trad_id) ).all()
    print(len(traducciones))

    for trad in traducciones: #for not necessary since there is only one
        trad.traduccion = translation
        trad.procesado = True
    session.commit()
    session.close()