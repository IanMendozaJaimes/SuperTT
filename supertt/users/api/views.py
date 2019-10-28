 
from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes
from rest_framework import permissions
from users.api.serializers import SerializadorUsuario, SerializadorRegistro
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

@api_view(['POST',])
@permission_classes([permissions.AllowAny])
def registration_view(request):#correo usado 10003, error -1, suyccess 1
    if request.method == 'POST':
        serializer = SerializadorRegistro(data = request.data)
        data = {}
        if serializer.is_valid():
            account = serializer.save()
            data['response'] = '1'
            data['email'] = account.email

            token = Token.objects.get(user=account).key
            data['token'] = token

            h = hashlib.sha1(request.POST['email'].encode('utf-8')).hexdigest()
            uh = UserHashes(user=account, hash=h, proposito=VALIDATE)
            uh.save()

            url = settings.SITE_URL + 'usuarios/validar?token=' + h

            e = Email()
            e.send_validation_email(request.POST['email'],request.POST['nombre'],url)
            e.close()
        else:
            data = serializer.errors #data = {"resultCode": "-1001"}
            data.update({"error":"customized"})
        return Response(data)

@api_view(['PUT'])
def edit_user_view(request, idUsuario):
    if request.method == 'PUT':
        usr = User(id = idUsuario)
        serializer = SerializadorUsuario(usr, data = request.data)
        data = {}
        if serializer.is_valid():
            account = serializer.save()
            data['resultCode'] = 1
            return Response(data =data)
        else:
            data = serializer.errors 
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
@permission_classes([permissions.AllowAny])
def login_view(request, *args, **kwargs):
        
        if request.method == 'POST':
            try:
                usr = User.objects.get(email=request.data['email'])
            except:
                return Response({"resultCode":"invalidCredentials"})
            
            pass_ok = check_password(request.data['password'], usr.password)
            if not pass_ok:
                return Response({"resultCode": "invalidPassword"})
            
            if not usr.is_active:
                return Response({"resultCode": "notActivated"})     
            print(usr.id)
            serializer = SerializadorUsuario(usr, data = request.data)
            data = {}
        if serializer.is_valid():
            print("third")
            token, created = Token.objects.get_or_create(user=usr)
            print("fourth")
            data.update({'user': usr.email})
            data.update({'token': token.key})
            data['codeStatus'] = '1'
            return Response(data =data)
        else:
            print("else")
            data = serializer.errors 
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)