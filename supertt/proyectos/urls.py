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
		#proyectos
		path('proyectos/todos', ProyectsView.as_view()),
		path('proyectos/traducciones', TranslationsView.as_view()),
		path('proyectos/nuevo', crearProyectoView),
		path('proyectos/descargar', CreateProyectFile),
		path('proyectos/eliminar', eliminarProyectoView),
		path('proyectos/cambiar', cambiarProyectoView),

		#-------------REST API VIEWS-------------
		#proyectos
		path('proyectos', create_project_view),
		path('proyectos/<idpro>', methods_project_view),
		path('usuarios/<usuario>/proyectos', detail_project_view),

		#traducciones
		path('traducciones', create_translation_view),
		path('traducciones/<idtraduccion>', methods_translation_view),
		path('proyectos/<idpro>/traducciones', detail_translation_view),
		#path('traducciones/<id>/delete', delete_translation_view),
	]

