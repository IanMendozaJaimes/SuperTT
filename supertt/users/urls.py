from django.urls import path
from .views import *
from .views import LoginView, SignUpView, ProfileView, PasswordRecoverView
from django.conf.urls import include, url

from users.api.urls import url_patterns
urlpatterns = [
    path('login', LoginView.as_view(), name="login_view"),
    path('registrarse', SignUpView.as_view(), name="sign_up_view"),
    path('perfil', ProfileView.as_view(), name="profile_view"),
    path('recuperacion', PasswordRecoverView.as_view()),
    path('cambiar', CambiarUsuarioView, name="cambiar_usuario_view"),
    path('cambiarContra', CambiarContraView, name="cambiar_contra_request"),
    path('cambiarFoto', CambiarFotoView),
    path('validar', ValidarUsuarioView, name="validar_usuario_view"),
    path('enviarEmailRecuparacion', EnviarCorreoRecuperacionView, 
        name="enviar_correo_recuperacion_view"),
    path('contrasena', RecuperarContraView, name="recuperar_contra_view"),
    path('cambiarC', CambiarCView.as_view(), name="cambiar_contra_view"),
    path('logout', LogoutView, name="logout_view")
]