from django.urls import path, include
from proyectos.views import *
from rest_framework import routers
from .views import *

router = routers.DefaultRouter()
router.register(r'proyectos', ProyectoLista)

urlpatterns = [
    path('', include(router.urls)),
    path('api-auth/', include('rest_framework.urls', namespace='rest_framework')),
]

def get_proyects_urls():
	return [
		path('proyectos/todos', ProyectsView.as_view()),
		path('proyectos/traducciones', TranslationsView.as_view()),
		path('proyectos/nuevo', crearProyectoView),
		path('proyectos/cambiar', cambiarProyectoView),
		path('proyectos/eliminar', eliminarProyectoView),
	]

