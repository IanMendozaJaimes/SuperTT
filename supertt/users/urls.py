from django.urls import path
from .views import LoginView, SignUpView, ProfileView, PasswordRecoverView

urlpatterns = [
    path('login', LoginView.as_view()),
    path('registrarse', SignUpView.as_view()),
    path('perfil', ProfileView.as_view()),
    path('recuperacion', PasswordRecoverView.as_view()),
]