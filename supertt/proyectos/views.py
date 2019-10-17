#from django.shortcuts import render
from rest_framework import generics, viewsets
from .models import Proyecto, Traduccion
from .serializers import SerializadorProyecto, SerializadorTraduccion
from django.shortcuts import get_object_or_404
from django.views.generic import TemplateView
from django.shortcuts import render, redirect

from django.contrib.auth.decorators import login_required
from django.contrib.auth.mixins import LoginRequiredMixin

from django.http import JsonResponse
from django.forms.models import model_to_dict

from rest_framework.decorators import api_view, permission_classes
from rest_framework import permissions
from rest_framework.permissions import IsAuthenticated

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
            pk = 1 ,
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


#---------------- REST API VIEWS------------------   
 
#--------------------views projects
@api_view(['POST', ])
@permission_classes([IsAuthenticated])
def create_project_view(request):

    usr = request.user#Account.objects.get(pk=1) ##later request.user
    proj = Proyecto(usuario = User(id=usr), nombre=request.data.get('nombre') , calificacion = 0.0)

    if request.method == "POST":
        serializer = SerializadorProyecto(proj, data = request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)
    

@api_view(['PUT', ])
@permission_classes((IsAuthenticated,))
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

@api_view(['DELETE', ])
@permission_classes((IsAuthenticated,))
def delete_project_view(request, id):
    try:
        proj = Proyecto.objects.get(id= id)
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

@api_view(['GET'])
@permission_classes([IsAuthenticated,])
def detail_project_view(request, usuario):
    print(request.user.id)
    try:
        proj = Proyecto.objects.filter(usuario = usuario)
    except Proyecto.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)
    user = request.user

    print("user {}:= {}".format(user.id, usuario))
    
    if (str(usuario) != str(request.user.id)) or (proj.count() > 0 and request.user.id != proj[0].usuario.id):
        return Response({"response": "Token: does not belong to user with id: {} ".format( usuario)})

    if request.method == "GET":
        serializer = SerializadorProyecto(proj, many=True)
        return Response(serializer.data)

#--------------views translations

@api_view(['POST', ])
@permission_classes([IsAuthenticated])
def methods_translation_view(request): #request must include idproyecto
    usr = request.user
    data = {}
    try:
        pro = Proyecto.objects.get(id=request.data.get('idproyecto'))
    except Proyecto.DoesNotExist:
        data['failure'] = "Invalid [projectid: {}] to save translation in".format(request.data.get('idproyecto'))
        return Response(data=data)
    trans = Traduccion(proyecto = Proyecto(id=request.data.get('idproyecto')), usuario =usr, nombre=request.data.get('nombre') , calificacion = 0.0, archivo = "", traduccion="")

    if request.method == "POST":
        serializer = SerializadorTraduccion(trans, data = request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST) 

@api_view(['PUT', 'DELETE'])
@permission_classes((permissions.IsAuthenticated,))
def methods_translation_view(request, idtraduccion):
    try:
        trans = Traduccion.objects.get(id= idtraduccion)
    except Traduccion.DoesNotExist:
        return Response("Not Found",status=status.HTTP_404_NOT_FOUND)
    
    user = request.user
    if trans.usuario != user:
        return Response({"response": "Token: {} does not belong to user with id: {} ".format(request.Authorization, user)})

    if request.method == "PUT":
        serializer = SerializadorTraduccion(trans, data=request.data)
        data = {}
       
        if serializer.is_valid():
            serializer.save()
            data["success"] = "update sucessful"
            return Response(data =data)
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)
    elif request.method == 'DELETE':
        operation = trans.delete()
        data = {}
        if operation:
            data["sucess"] = "delete successful"
        else:
            data["failure"] = "delete failed"
        return Response(serializer.errors, status = status.HTTP_404_NOT_FOUND)

# @api_view(['DELETE', ])
# @permission_classes((permissions.AllowAny,))
# def delete_translation_view(request, id):
#     try:
#         trans = Traduccion.objects.get(id= id)
#     except Traduccion.DoesNotExist:
#         return Response(status=status.HTTP_404_NOT_FOUND)
#     if request.method == "DELETE":
#         operation = trans.delete()
#         data = {}
#         if operation:
#             data["sucess"] = "delete successful"
#         else:
#             data["failure"] = "delete failed"
#         return Response(data = data)

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


       