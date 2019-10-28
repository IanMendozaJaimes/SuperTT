from django.urls import path
from .views import *
from .views import LoginView, SignUpView, ProfileView, PasswordRecoverView
from django.conf.urls import include, url

from users.api.urls import url_patterns
urlpatterns = [
    path('login', LoginView.as_view()),
    path('registrarse', SignUpView.as_view()),
    path('perfil', ProfileView.as_view()),
    path('recuperacion', PasswordRecoverView.as_view()),
    path('cambiar', CambiarUsuarioView),
    path('cambiarContra', CambiarContraView),
    path('cambiarFoto', CambiarFotoView),
    path('validar', ValidarUsuarioView),
    path('enviarEmailRecuparacion', EnviarCorreoRecuperacionView),
    path('contrasena', RecuperarContraView),
    path('cambiarC', CambiarCView.as_view()),
    path('logout', LogoutView)
]