from django.urls import path, include
from proyectos.views import *
from rest_framework import routers
from .views import ProyectoLista

router = routers.DefaultRouter()
router.register(r'proyectos', ProyectoLista)

urlpatterns = [
    path('', include(router.urls)),
    path('api-auth/', include('rest_framework.urls', namespace='rest_framework')),
]