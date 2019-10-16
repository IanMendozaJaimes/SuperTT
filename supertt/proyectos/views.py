#from django.shortcuts import render
from rest_framework import generics, viewsets
from .models import Proyecto, Traduccion
from .serializers import SerializadorProyecto, TraductionSerializer
from django.shortcuts import get_object_or_404
from django.views.generic import TemplateView
from django.shortcuts import render, redirect

from django.contrib.auth.decorators import login_required
from django.contrib.auth.mixins import LoginRequiredMixin

from django.http import JsonResponse
from django.forms.models import model_to_dict


from general.util import *

from rest_framework.decorators import api_view, permission_classes
from rest_framework import permissions

import pytz
from django.utils import timezone
from datetime import datetime


from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view

from users.models import User
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
            pk = 1 ,
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


@api_view(['PUT', ])
@permission_classes((permissions.AllowAny,))
def edit_project_view(request, idpro):
    try:
        proj = Proyecto.objects.get(id= idpro)
    except Proyecto.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)
    if request.method == "PUT":
        serializer = SerializadorProyecto(proj, data=request.data)
        data = {}
       
        if serializer.is_valid():
            serializer.save()
            data["success"] = "update sucessful"
            return Response(data =data)
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)

@api_view(['GET'])
@permission_classes((permissions.AllowAny,))
def detail_project_view(request, usuario):
    
    try:
        proj = Proyecto.objects.filter(usuario = usuario)
    except Proyecto.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)
    if request.method == "GET":
        serializer = SerializadorProyecto(proj, many=True)
        return Response(serializer.data)
    """import json
    from django.core.serializers.json import DjangoJSONEncoder

    projs = (Proyecto.objects.filter(usuario=usuario).values('usuario', 'nombre', 'fechaModificacion', 'fechaCreacion', 'calificacion'))
    json_projs = json.dumps(list(projs), sort_keys=True, indent=1, cls=DjangoJSONEncoder)

    return Response(json_projs.replace('\n', ' ').replace('"', ' ').lstrip() ) """


@api_view(['DELETE', ])
@permission_classes((permissions.AllowAny,))
def delete_project_view(request, idpro):
    try:
        proj = Proyecto.objects.get(id= idpro)
    except Proyecto.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)
    if request.method == "DELETE":
        operation = proj.delete()
        data = {}
        if operation:
            data["sucess"] = "delete successful"
        else:
            data["failure"] = "delete failed"
        return Response(data = data)

@api_view(['POST', ])
@permission_classes((permissions.AllowAny,))
def create_project_view(request):

    usr = request.user#Account.objects.get(pk=1) ##later request.user
    proj = Proyecto(usuario = User(id=usr), nombre=request.data.get('nombre') , calificacion = 0.0)

    if request.method == "POST":
        serializer = SerializadorProyecto(proj, data = request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)

	# usuario = models.ForeignKey(User, on_delete=models.CASCADE)
	# nombre = models.CharField(max_length=200)
	# fechaModificacion = models.DateTimeField(auto_now=True)
	# fechaCreacion = models.DateTimeField(auto_now_add=True)
	# calificacion = models.DecimalField(max_digits=3, decimal_places=2)










@api_view(['PUT', ])
@permission_classes((permissions.AllowAny,))
def edit_translation_view(request, idtrans):
    try:
        trans = Traduccion.objects.get(id= idpro)
    except Proyecto.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)
    if request.method == "PUT":
        serializer = TraductionSerializer(trans, data=request.data)
        data = {}
       
        if serializer.is_valid():
            serializer.save()
            data["success"] = "update sucessful"
            return Response(data =data)
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)

@api_view(['GET'])
@permission_classes((permissions.AllowAny,))
def detail_translation_view(request, idpro):
    
    try:
        trans = Traduccion.objects.filter(proyecto = idpro)
    except Traduccion.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)
    if request.method == "GET":
        serializer = TraductionSerializer(trans, many=True)
        return Response(serializer.data)


@api_view(['DELETE', ])
@permission_classes((permissions.AllowAny,))
def delete_translation_view(request, idtrans):
    try:
        trans = Traduccion.objects.get(id= idtrans)
    except Traduccion.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)
    if request.method == "DELETE":
        operation = trans.delete()
        data = {}
        if operation:
            data["sucess"] = "delete successful"
        else:
            data["failure"] = "delete failed"
        return Response(data = data)

@api_view(['POST', ])
@permission_classes((permissions.AllowAny,))
def create_translation_view(request):

    usr = request.user#Account.objects.get(pk=1) ##later request.user
    #corregir #trans = Traduccion(usuario = User(id=usr), nombre=request.data.get('nombre') , calificacion = 0.0)

    if request.method == "POST":
        serializer = SerializadorTraduccion(trans, data = request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)