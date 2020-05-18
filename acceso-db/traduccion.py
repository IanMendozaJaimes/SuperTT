from sqlalchemy import Column, String, Integer, DateTime, Boolean, Numeric

from base import Base


class Traduccion(Base):
    __tablename__ = 'proyectos_traduccion'

    id = Column(Integer, primary_key=True)
    nombre = Column(String)
    calificacion = Column(Numeric)
    archivo = Column(String)
    traduccion = Column(String)
    proyecto_id = Column(Integer)
    usuario_id = Column(Integer)
    fechaCreacion = Column(DateTime)
    procesado = Column(Boolean)

    def __repr__(self):
        repre = f"<Traduccion(id={self.id}, proyecto_id={self.proyecto_id}, "
        repre = repre + f"usuario_id={self.usuario_id}, nombre={self.nombre}, "
        repre = repre + f"archivo={self.archivo}, procesado={self.procesado}>"
        return repre