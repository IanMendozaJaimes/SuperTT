from users.models import User
from rest_framework import serializers

from django.contrib.auth.hashers import check_password, make_password
class SerializadorRegistro(serializers.ModelSerializer):

    password2 = serializers.CharField(style = {'input_type': 'password'}, write_only= True)
    class Meta:
        model = User
        fields = ('email','estudios', 'first_name', 'password', 'password2')
        extra_kwargs = {
            'password': {'write_only': True}
        }
    def save(self, data):

        try:
            usr = User.objects.get(email=self.data['email'])
            raise Response({"resultCode": -1})
        except:
            pass
        
        password = self.validated_data['password']
        password2 = self.validated_data['password2']

        encrypted_password = make_password(password)
        print(self.data)

        #, first_name = request.data.get('nombre')
        account = User(
            email = self.validated_data['email'], password = encrypted_password, first_name = data.get('nombre'), last_name =  data.get('apellido')
        )
        
        if password != password2:
            raise serializers.ValidationError({'resultCode': 123456789})
        print(encrypted_password)
        #account.set_password(encrypted_password)
        account.save()
        return account
class SerializadorUsuario(serializers.ModelSerializer):

    class Meta:
        model = User
        fields = ('id', 'email', 'estudios', 'password', 'imagen_perfil')
class SerializadorUsuarioEdit(serializers.ModelSerializer):
    #password2 = serializers.CharField(style = {'input_type': 'password'}, write_only= True)
    class Meta:
        model = User
        fields = ('first_name', 'last_name',)
    def save(self, data, idUsuario):

        try:
            usr = User.objects.get(id = idUsuario)
        except User.DoesNotExist:
            raise Response({"resultCode": -1})#pass
        
        if len(data['password']) > 0:
            usr.password = make_password(data.get('password'))
        else:
            usr.password = str(usr.password)
        usr.first_name = data.get('nombre')
        usr.last_name = data.get('apellido')
        

        usr.save()
        #, first_name = request.data.get('nombre')
        #account = User( email = usr.email,password = encrypted_password, first_name = data.get('nombre'), last_name =  data.get('apellido')
        #)
        #account.set_password(encrypted_password)
        #account.save()
        return usr