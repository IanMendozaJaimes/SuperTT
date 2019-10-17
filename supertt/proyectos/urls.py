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

		#-------------REST API VIEWS-------------
		#proyectos
		path('usuarios/<usuario>/proyectos', detail_project_view),
		path('proyectos/create', create_project_view),
		path('proyectos/<id>/delete', delete_project_view),
		path('proyectos/<idpro>/update', edit_project_view),

		#traducciones
		path('traducciones', methods_translation_view),
		path('traducciones/<idtraduccion>', methods_translation_view),
		path('proyectos/<idpro>/traducciones', detail_translation_view),
		#path('traducciones/<id>/delete', delete_translation_view),
		
		
	]

