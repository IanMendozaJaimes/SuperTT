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

from general.util import *

# Create your views here.

class ProyectsView(LoginRequiredMixin, TemplateView):
    login_url = '/usuarios/login'
    template_name = 'proyectos.html'

    def process_request(self, request, *args, **kwargs):

        projects = Proyecto.objects.filter(usuario=request.user)
        data = list(projects.values())

        data = list(map(change_time_zone, data))

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

    def process_request(self, request, *args, **kwargs):

        try:
            project = Proyecto.objects.get(usuario=request.user,pk=int(request.GET['id']))
            p = {
                'id': project.id,
                'usuario': project.usuario,
                'nombre': project.nombre,
                'fechaModificacion': project.fechaModificacion,
                'fechaCreacion': project.fechaCreacion,
                'calificacion': project.calificacion,
            }
            p = change_time_zone(p)

            translations = list(Traduccion.objects.filter(proyecto=project))

            return render(request, self.template_name, {
                'nombre' : request.user.first_name + ' ' + request.user.last_name,
                'proyecto': p,
                'traducciones': translations,
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
    v = Validator()
    m = Message()
    usr = request.user
    nom = request.GET['nombre'].replace('%', ' ')

    if not v.field(nom):
        m.add_validation_error(REQUIRED_FIELD, 'nombre')
    elif v.proyect_exists(usr, nom):
        m.add_validation_error(PROYECT_ALREADY_EXISTS, 'nombre')

    if m.get_len() == 0:
        project = Proyecto(usuario=usr, nombre=nom, calificacion=0)
        project.save()
        p = {
            'err': {},
            'id': project.id,
            'fechaModificacion': project.fechaModificacion,
            'fechaCreacion': project.fechaCreacion,
        }
        p = change_time_zone(p)
        return JsonResponse(p)

    return JsonResponse({'err':m.get_messages()})


def cambiarProyectoView(request):
    v = Validator()
    m = Message()
    usr = request.user
    nom = request.GET['nombre'].replace('%', ' ')
    old_nom = request.GET['oldnombre'].replace('%', ' ')

    if not v.field(nom):
        m.add_validation_error(REQUIRED_FIELD, 'nombre')
    elif v.proyect_exists(usr, nom):
        m.add_validation_error(PROYECT_ALREADY_EXISTS, 'nombre')

    if m.get_len() == 0:
        Proyecto.objects.filter(usuario=usr, nombre=old_nom).update(nombre=nom)
        project = Proyecto.objects.get(usuario=usr, nombre=nom)
        p = {
            'err': {},
            'fechaModificacion': project.fechaModificacion,
            'fechaCreacion': project.fechaCreacion,
        }
        p = change_time_zone(p)
        return JsonResponse(p)

    return JsonResponse({'err':m.get_messages()})


def eliminarProyectoView(request):
    usr = request.user
    idp = request.GET['id']
    Proyecto.objects.filter(pk=idp).delete()
    return JsonResponse({'err':{}})












