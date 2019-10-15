from users.models import User
from rest_framework import serializers

class SerializadorRegistro(serializers.ModelSerializer):

    password2 = serializers.CharField(style = {'input_type': 'password'}, write_only= True)
    class Meta:
        model = User
        fields = ('email','estudios', 'password', 'password2')
        extra_kwargs = {
            'password': {'write_only': True}
        }
    def save(self):
        account = User(
            email = self.validated_data['email'],
        )
        password = self.validated_data['password']
        password2 = self.validated_data['password2']

        if password != password2:
            raise serializers.ValidationError({'password': 'Contraseñas deben coincidir'})
        account.set_password(password)
        account.save()
        return account