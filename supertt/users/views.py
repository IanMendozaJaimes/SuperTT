from django.shortcuts import render
from django.views.generic import TemplateView
from .models import UsersInformation


class LoginView(TemplateView):
	template_name = 'login.html'


class SignUpView(TemplateView):
	template_name = 'registro.html'

	def check_field(self, f):
		if f == None:
			return False
		if len(f) == 0:
			return False
		return True

	def validate_data(self, request):
		if check_field(request.nombre):
			return False
		if check_field(request.correo):
			return False
		if check_field(request.contra):
			return False
		if check_field(request.contraDos):
			return False
		return True

	def get(self, request, *args, **kwargs):
		return render(request, self.template_name)

	# def post(self, request, *args, **kwargs):
	# 	if validate_data(request):
	# 	else




class ProfileView(TemplateView):
	template_name = 'perfil.html'


class PasswordRecoverView(TemplateView):
	template_name = 'recuperacion.html'


