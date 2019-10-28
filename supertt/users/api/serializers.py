from users.models import User
from rest_framework import serializers

from django.contrib.auth.hashers import check_password, make_password
class SerializadorRegistro(serializers.ModelSerializer):

    password2 = serializers.CharField(style = {'input_type': 'password'}, write_only= True)
    class Meta:
        model = User
        fields = ('email','estudios', 'password', 'password2')
        extra_kwargs = {
            'password': {'write_only': True}
        }
    def save(self):

        try:
            usr = User.objects.get(email=request.data['email'])
            raise Response({"resultCode": -1})
        except:
            pass

        account = User(
            email = self.validated_data['email'],
        )
        password = self.validated_data['password']
        password2 = self.validated_data['password2']

        if password != password2:
            raise serializers.ValidationError({'resultCode': 123456789})
        
        account.set_password(make_password(password))
        account.save()
        return account
class SerializadorUsuario(serializers.ModelSerializer):

    class Meta:
        model = User
        fields = ('id', 'email', 'estudios', 'password', 'imagen_perfil')
    