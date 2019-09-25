from django.conf.urls import url
from proyectos.views import *

urlpatterns = [
    url(r'^proyectos/$', ProyectoLista.as_view(), name = 'proyectos')
]