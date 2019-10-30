from .models import Proyecto, Traduccion
from rest_framework import serializers

class SerializadorProyecto(serializers.ModelSerializer):

	class Meta:
	    model = Proyecto
	    fields = ('id', 'usuario','nombre','fechaModificacion', 'fechaCreacion', 'calificacion')
class SerializadorProyectoNombre(serializers.ModelSerializer):

	class Meta:
		model = Proyecto
		fields = ('nombre', )
class SerializadorTraduccion(serializers.ModelSerializer):
	
	class Meta:
		model = Traduccion
		fields = ('id', 'proyecto', 'usuario', 'nombre', 'calificacion', 'archivo', 'traduccion')


	# 	proyecto = models.ForeignKey(Proyecto, models.SET_NULL, blank=True, null=True)
	# usuario = models.ForeignKey(User, models.SET_NULL, blank=True, null=True)
	# nombre = models.CharField(max_length=200)
	# calificacion = models.DecimalField(max_digits=3, decimal_places=2)
	# archivo = models.TextField()
	# traduccion = models.TextField()