from django.db import models
from users.models import User


class Proyecto(models.Model):
	usuario = models.ForeignKey(User, on_delete=models.CASCADE)
	nombre = models.CharField(max_length=200)
	fechaModificacion = models.DateTimeField(auto_now=True)
	fechaCreacion = models.DateTimeField(auto_now_add=True)
	calificacion = models.DecimalField(max_digits=3, decimal_places=2, blank=True)


class Traduccion(models.Model):
	proyecto = models.ForeignKey(Proyecto, models.SET_NULL, blank=True, null=True)
	usuario = models.ForeignKey(User, models.SET_NULL, blank=True, null=True)
	nombre = models.CharField(max_length=200,blank=True)
	calificacion = models.DecimalField(max_digits=3, decimal_places=2, blank=True)
	archivo = models.TextField(blank=True)
	traduccion = models.TextField(blank=True)
	fechaCreacion = models.DateTimeField(auto_now_add=True, null=True)


