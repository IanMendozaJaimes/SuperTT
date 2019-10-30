 
from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes, authentication_classes
from rest_framework import permissions
from rest_framework import authentication
from users.api.serializers import SerializadorUsuario, SerializadorRegistro, SerializadorUsuarioEdit
from rest_framework.permissions import IsAuthenticated
from rest_framework.authtoken.models import Token
from users.models import User


from rest_framework import parsers, renderers
from rest_framework.authtoken.models import Token
from rest_framework.authtoken.serializers import AuthTokenSerializer
from rest_framework.compat import coreapi, coreschema
from rest_framework.response import Response
from rest_framework.schemas import ManualSchema
from rest_framework.views import APIView

from django.contrib.auth.hashers import check_password, make_password
import hashlib
from general.util import *

import random
@api_view(['POST',])
@permission_classes([permissions.AllowAny])
def registration_view(request):#correo usado 10003, error -1, suyccess 1
    if request.method == 'POST':
        print(request.POST)
        serializer = SerializadorRegistro(data = request.data)
        print(request.data.get('nombre'))
        data = {}
        if serializer.is_valid():
            print(request.data['nombre'])
            account = serializer.save(request.data)
            data['resultCode'] = 1
            #data['email'] = account.email

            token = Token.objects.get(user=account).key
            #data['token'] = token

            h = hashlib.sha1(request.data['email'].encode('utf-8')).hexdigest()
            uh = UserHashes(user=account, hash=h, proposito=VALIDATE)
            uh.save()

            url = settings.SITE_URL + 'usuarios/validar?token=' + h
            print(url)

            e = Email()
            e.send_validation_email(request.data['email'],request.data['nombre'],url)
            e.close()
        else:
            #data = serializer.errors #data = {"resultCode": "-1001"}
            data.update({"resultCode": -1003})
        return Response(data)

@api_view(['PUT'])
def edit_user_view(request, idUsuario):
    if request.method == 'PUT':
        usr = request.user
        pass_ok = check_password(request.data['password2'], usr.password)
        if not pass_ok:
            print("---------------------not pass")
            return Response({"resultCode": -1001})
        print("passed ----------------------")
        
        print("passed ----------------------2")
        serializer = SerializadorUsuarioEdit(usr, data = request.data)
        data = {}   
        print("passed ----------------------3")
        if serializer.is_valid():
            serializer.save(request.data, idUsuario)
            print("abcd")
            data['resultCode'] = 1
            return Response(data =data)
        else:
            print("passed ----------------------4")
            data = {"resultCode": -1}
        return Response(data= data, status = status.HTTP_400_BAD_REQUEST)
 
@api_view(['POST'])
@permission_classes([permissions.AllowAny])
def login_view(request, *args, **kwargs):
        
        if request.method == 'POST':
            try:
                usr = User.objects.get(email=request.data['email'])
            except:
                return Response({"resultCode":-1001})
            
            pass_ok = check_password(request.data['password'], usr.password)
            
            if not pass_ok:
                return Response({"resultCode": -1001})
            
            if not usr.is_active:
                return Response({"resultCode": -1006})     
            print(usr.id)
            serializer = SerializadorUsuario(usr, data = request.data)
            data = {}
        if serializer.is_valid():
            token, created = Token.objects.get_or_create(user=usr)
            data.update({'email': usr.email})
            data.update({'token': token.key})

            data.update({'idUsuario': usr.id})
            data.update({'apellido': usr.last_name})
            data.update({'nombre': usr.first_name})
            data.update({'urlImg': usr.imagen_perfil})
            #email, token, idusuario, apellido, nombre, urlImg
            data['resultCode'] = 1
            return Response(data =data)
        else:
            data['resultCode'] = -1
            #data = serializer.errors
        return Response(data, status = status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
@permission_classes([permissions.AllowAny])
def recover_password_view(request, *args, **kwargs):
    email = request.data.get('email')
    try:
        user = User.objects.get(email = email)
    except User.DoesNotExist:
        return Response({"resultCode": -1006})

    hemail = email + str(random.randrange(0,1000))
    
    h = hashlib.sha1(hemail.encode('utf-8')).hexdigest()
    
    uh = UserHashes(user=user, hash=h, proposito=CHANGE_PASSWORD)
    uh.save()

    url = settings.SITE_URL + 'usuarios/contrasena?token=' + h

    e = Email()
    e.send_change_password_email(email, user.first_name, url)
    e.close()
    return Response({"resultCode": 1})