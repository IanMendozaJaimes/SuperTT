from .models import Proyecto
from rest_framework import serializers

class SerializadorProyecto(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Proyecto
        fields = ('usuario','nombre','fechaModificacion', 'fechaCreacion', 'calificacion')
        