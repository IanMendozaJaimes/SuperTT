#from django.shortcuts import render
from rest_framework import generics, viewsets
from .models import Proyecto
from .serializers import SerializadorProyecto
from django.shortcuts import get_object_or_404
from django.views.generic import TemplateView
from django.shortcuts import render, redirect

from django.contrib.auth.decorators import login_required
from django.contrib.auth.mixins import LoginRequiredMixin

from django.http import JsonResponse
from django.forms.models import model_to_dict

import pytz
from django.utils import timezone
from datetime import datetime

# Create your views here.

class ProyectsView(LoginRequiredMixin, TemplateView):
    login_url = '/usuarios/login'
    template_name = 'proyectos.html'

    def change_time_zone(self, data):
        tz = pytz.timezone('America/Mexico_City')
        
        t = timezone.localtime(data['fechaCreacion'], tz)
        data['fechaCreacion'] = datetime(t.year, t.month, t.day, 
            t.hour, t.minute, t.second, t.microsecond)
        
        t = timezone.localtime(data['fechaModificacion'], tz)
        data['fechaModificacion'] = datetime(t.year, t.month, t.day, 
            t.hour, t.minute, t.second, t.microsecond)

        return data

    def process_request(self, request, *args, **kwargs):

        projects = Proyecto.objects.filter(usuario=request.user)
        data = list(projects.values())

        data = list(map(self.change_time_zone, data))

        return render(request, self.template_name, 
            {'nombre' : request.user.first_name + ' ' + request.user.last_name,
             'proyectos': data })


    def get(self, request, *args, **kwargs):
        return self.process_request(request, args, kwargs)

    def post(self, request, *args, **kwargs):
        return self.process_request(request, args, kwargs)



class TranslationsView(LoginRequiredMixin, TemplateView):
    login_url = '/usuarios/login'
    template_name = 'traducciones.html'

    def change_time_zone(self, data):
        tz = pytz.timezone('America/Mexico_City')
        
        t = timezone.localtime(data['fechaCreacion'], tz)
        data['fechaCreacion'] = datetime(t.year, t.month, t.day, 
            t.hour, t.minute, t.second, t.microsecond)
        
        t = timezone.localtime(data['fechaModificacion'], tz)
        data['fechaModificacion'] = datetime(t.year, t.month, t.day, 
            t.hour, t.minute, t.second, t.microsecond)

        return data

    def process_request(self, request, *args, **kwargs):

        try:
            project = Proyecto.objects.get(usuario=request.user,pk=int(request.GET['id']))
            project = {
                'usuario': project.usuario,
                'nombre': project.nombre,
                'fechaModificacion': project.fechaModificacion,
                'fechaCreacion': project.fechaCreacion,
                'calificacion': project.calificacion,
            }
            project = self.change_time_zone(project)

            print(project)

            return render(request, self.template_name, {
                'nombre' : request.user.first_name + ' ' + request.user.last_name,
                'proyecto': project,
            })

        except Exception as e:
            return redirect('/proyectos/todos')

    def get(self, request, *args, **kwargs):
        return self.process_request(request, args, kwargs)

    def post(self, request, *args, **kwargs):
        return self.process_request(request, args, kwargs)



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


def crearProyectoView(request):
    #primero hay que validar el nombre
    err = dict()
    usr = request.user
    nom = request.GET['nombre'].replace('%', ' ')
    proyect = Proyecto(usuario=usr, nombre=nom, calificacion=0)
    proyect.save()
    return JsonResponse({'err':err})











