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


from general.util import *

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

    def process_request(self, request, *args, **kwargs):

        projects = Proyecto.objects.filter(usuario=request.user)
        data = list(projects.values())

        data = list(map(change_time_zone, data))

        return render(request, self.template_name, 
            {
                'nombre' : request.user.first_name + ' ' + request.user.last_name,
                'avatar': 'imgUsuario/'+request.user.imagen_perfil,
                'proyectos': data,
            })


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
                'avatar': 'imgUsuario/'+request.user.imagen_perfil,
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


def CreateProyectFile(request):
    if request.method == 'POST':
        user = request.user
        proyecto = request.POST['proyecto']
        traducciones = Traduccion.objects.filter(proyecto=proyecto)
        nombre = request.POST['proyecto_nombre'].replace(' ', '_') + user.first_name.replace(' ', '_') 
        
        texto = ''
        for t in traducciones:
            texto += t.traduccion + '\n\n'

        archivo = ''
        with open(settings.BASE_DIR+'/general/project_file.tex', 'r') as file:
            archivo = file.read()

        archivo = archivo.replace('DOCUMENT_CONTENT', texto)

        with open(settings.BASE_DIR+'/static/files/'+nombre+'.tex', 'w') as file:
            file.write(archivo)

        return JsonResponse({'err':{}, 'url_file':settings.SITE_URL+'static/files/'+nombre+'.tex'})

    else:
        return JsonResponse({'err':{'bad_method':'bad'}})


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



#---------------- REST API VIEWS------------------   
 
#--------------------views projects
@api_view(['POST', ])
@permission_classes([IsAuthenticated])
def create_project_view(request):

    usr = request.user#Account.objects.get(pk=1) ##later request.user

    ### Verify this validation
    try:
        proj = Proyecto(usuario = User(id=usr), nombre=request.data.get('nombre') , calificacion = 0.0)
    except not Proyecto.DoesNotExist: 
        return Response({"resultCode": "-1"})
    
    if request.method == "POST":
        serializer = SerializadorProyecto(proj, data = request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response({"resultCode": "-1001"}, status = status.HTTP_400_BAD_REQUEST)
    

@api_view(['PUT', 'DELETE'])
@permission_classes((IsAuthenticated,))
def methods_project_view(request, idpro):
    try:
        proj = Proyecto.objects.get(id= idpro)
    except Proyecto.DoesNotExist:
        return Response({"resultCode": -1}, status=status.HTTP_404_NOT_FOUND)
    usr = request.user
    if usr != proj.usuario:
        return Response("Project with id {} does not belong to user with id {}".format(proj.usuario, usr), status=status.HTTP_403_FORBIDDEN)
    if request.method == "PUT":
        serializer = SerializadorProyecto(proj, data=request.data)
        data = {}
       
        if serializer.is_valid():
            serializer.save()
            data["success"] = "update sucessful"
            return Response(data =data)
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)
    elif request.method == 'DELETE':
        operation = proj.delete()
        data = {}
        if operation:
            data["resultCode"] = "1"
        else:
            data["resultCode"] = "-1"
        return Response(data = data)

# @api_view(['DELETE', ])
# @permission_classes((IsAuthenticated,))
# def delete_project_view(request, id):
#     try:
#         proj = Proyecto.objects.get(id= id)
#     except Proyecto.DoesNotExist:
#         return Response(status=status.HTTP_404_NOT_FOUND)
#     if request.method == "DELETE":
#         operation = proj.delete()
#         data = {}
#         if operation:
#             data["sucess"] = "delete successful"
#         else:
#             data["failure"] = "delete failed"
#         return Response(data = data)

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
    #user validation
    if (str(usuario) != str(request.user.id)) or (proj.count() > 0 and request.user.id != proj[0].usuario.id):
        return Response({"response": "Token: does not belong to user with id: {} ".format( usuario)})

    if request.method == "GET":
        serializer = SerializadorProyecto(proj, many=True)
        return Response(serializer.data)

#--------------views translations

@api_view(['POST', ])
@permission_classes([IsAuthenticated])
def create_translation_view(request): #request must include idproyecto in body
    usr = request.user
    data = {}
    try:
        pro = Proyecto.objects.get(id=request.data.get('idproyecto'))
    except Proyecto.DoesNotExist:
        data['failure'] = "Invalid [idproyecto: {}] to save translation in".format(request.data.get('idproyecto'))
        return Response(data=data)
    #validate user
    if usr != pro.usuario:
        return Response("Project with id {} does not belong to user with id {}".format(pro.id, usr.id), status=status.HTTP_403_FORBIDDEN)


    try:
        file = request.data['file']
    except KeyError:
        raise ParseError('Request has no resource file attached')
        product = Product.objects.create(image=file, ....)    
    

    image_file = open(str(usr) + str(request.data.get('idproyecto')))
    image_file.write(file.text)

    trans = Traduccion(proyecto = Proyecto(id=request.data.get('idproyecto')), usuario =usr , calificacion = 0.0, archivo = "", traduccion="")
    trans.nombre = str(trans.fechaCreacion)
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
        data = {}
        data['resultCode'] = "-1"
        return Response(data, status=status.HTTP_404_NOT_FOUND)
    user = request.user

    #validate user is the owner of the project/translation
    if request.user != trans.usuario:
        return Response("Translation with id {} does not belong to user with id {}".format(trans.id, user.id), status=status.HTTP_403_FORBIDDEN)

    #if trans.usuario != user:
        #return Response({"response": "Token: {} does not belong to user with id: {} ".format(request.Authorization, user)})

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
            data["resultCode"] = "1"
        else:
            data["resultCode"] = "-1"
        return Response(data = data)

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
@permission_classes((permissions.IsAuthenticated,))
def detail_translation_view(request, idpro):
    
    try:
        trans = Traduccion.objects.filter(proyecto = idpro)
    except Traduccion.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)
        
    if request.method == "GET":
        serializer = SerializadorTraduccion(trans, many=True)
        return Response(serializer.data)