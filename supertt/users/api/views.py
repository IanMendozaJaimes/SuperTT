 
from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes
from rest_framework import permissions
from users.api.serializers import SerializadorRegistro
from rest_framework.permissions import IsAuthenticated
from rest_framework.authtoken.models import Token

@api_view(['POST',])
def registration_view(request):
    if request.method == 'POST':
        serializer = SerializadorRegistro(data = request.data)
        data = {}
        if serializer.is_valid():
            account = serializer.save()
            data['response'] = 'sucessfully registered new user'
            data['email'] = account.email

            token = Token.objects.get(user=account).key
            data['token'] = token
        else:
            data = serializer.errors 
        return Response(data)