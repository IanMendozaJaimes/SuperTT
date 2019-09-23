from django.db import models
from django.contrib.auth.models import User


class Estudios(models.Model):
	grado = models.CharField(max_length=200)

class UsersInformation(models.Model):
	user = models.OneToOneField(User, on_delete=models.CASCADE)
	estudios = models.OneToOneField(Estudios, on_delete=models.SET_NULL, blank=True, null=True)
	imagen_perfil = models.TextField()


