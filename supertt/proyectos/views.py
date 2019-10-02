#from django.shortcuts import render
from rest_framework import generics, viewsets
from .models import Proyecto
from .serializers import SerializadorProyecto
from django.shortcuts import get_object_or_404
from django.views.generic import TemplateView
# Create your views here.


class ProyectsView(TemplateView):
	template_name = 'proyectos.html'


class TranslationsView(TemplateView):
	template_name = 'traducciones.html'


class ProyectoLista(viewsets.ModelViewSet):
    queryset = Proyecto.objects.all()
    serializer_class = SerializadorProyecto

    def get_object(self):
        queryset = self.get_queryset()
            
        obj = get_object_or_404(
            queryset,
            pk = 1,
        )
        return obj
