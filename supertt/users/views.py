from django.shortcuts import render, redirect
from django.contrib.auth import login
from django.views.generic import TemplateView
from .models import User
from django.core.exceptions import ObjectDoesNotExist
from django.contrib.auth.hashers import check_password, make_password
from django.contrib.auth.mixins import LoginRequiredMixin
from general.messages import *

import re


class Validator():

	def are_the_same(self, a, b):
		if a != b:
			return False
		return True 

	def field(self, field):
		if field is None:
			return False
		if len(field) == 0:
			return False
		return True

	def user_exists(self, user):
		try:
			u = User.objects.get(email=user)
			return True
		except ObjectDoesNotExist:
			return False

	def name(self, name):
		r = re.findall(r'[a-zA-Z ]+', name)
		if len(r) != 1:
			return False
		return True

	def email(self, mail):
		if ' ' in mail or len(mail) == 0:
			return False
		if re.search(r'\w+@\w+(\.\w+)+', mail) is None:
			return False
		return True



class LoginView(TemplateView):
	template_name = 'login.html'

	def authenticate(self, user, password):
		try:
			u = User.objects.get(email=user)
			pass_ok = check_password(password, u.password)
			if pass_ok:
				return u
			return None
		except:
			return None

	def validate_data(self, data):
		v = Validator()
		m = Message()

		if not v.field(data['correo']):
			m.add_validation_error(REQUIRED_FIELD, 'correo')
		elif not v.email(data['correo']):
			m.add_validation_error(INVALID_EMAIL, 'correo')

		if not v.field(data['contra']):
			m.add_validation_error(REQUIRED_FIELD, 'contra')

		return m

	def get(self, request, *args, **kwargs):
		resp = request.session.get('resp')
		if resp is not None:
			del request.session['resp']
			return render(request, self.template_name, resp)
		return render(request, self.template_name)

	def post(self, request, *args, **kwargs):
		err = self.validate_data(request.POST)

		if err.get_len() == 0:
			user = self.authenticate(request.POST['correo'], request.POST['contra'])
			if user is not None:
				login(request, user)
				return redirect('/proyectos/todos')
			else:
				m = Message()
				m.add_validation_error(INVALID_LOGIN, 'baduser')
				e = m.get_messages()
				e.update({
					'correo': request.POST['correo'],
				})
				return render(request, self.template_name, e)
		else:
			e = err.get_messages()
			e.update({
				'correo': request.POST['correo'],
			})
			return render(request, self.template_name, e)


class SignUpView(TemplateView):
	template_name = 'registro.html'

	def validate_data(self, data):
		m = Message()
		v = Validator()

		if not v.field(data['correo']):
			m.add_validation_error(REQUIRED_FIELD, 'correo')
		elif not v.email(data['correo']):
			m.add_validation_error(INVALID_EMAIL, 'correo')
		elif v.user_exists(data['correo']):
			m.add_validation_error(EMAIL_ALREADY_TAKEN, 'correo')

		if not v.field(data['nombre']):
			m.add_validation_error(REQUIRED_FIELD, 'nombre')
		elif not v.name(data['nombre']):
			m.add_validation_error(INVALID_NAME, 'nombre')

		if not v.field(data['contra']):
			m.add_validation_error(REQUIRED_FIELD, 'contra')

		if not v.field(data['contraConf']):
			m.add_validation_error(REQUIRED_FIELD, 'contraConf')

		if not v.are_the_same(data['contra'], data['contraConf']):
			m.add_validation_error(PASSWORDS_ARE_NOT_THE_SAME, 'contraConf')

		return m

	def get(self, request, *args, **kwargs):
		return render(request, self.template_name)

	def post(self, request, *args, **kwargs):
		err = self.validate_data(request.POST)

		if err.get_len() == 0:
			new_user = User(email=request.POST['correo'], 
							password=make_password(request.POST['contra']), 
							first_name=request.POST['nombre'])
			new_user.save()
			m = Message()
			m.add_alert(SEND_EMAIL, 'Tu cuenta se registro exitosamente!')
			request.session['resp'] = m.get_messages()
			return redirect('/usuarios/login')
		else:
			e = err.get_messages()
			e.update({
				'nombre': request.POST['nombre'],
				'correo': request.POST['correo'],
			})
			return render(request, self.template_name, e)




class ProfileView(LoginRequiredMixin, TemplateView):
	login_url = '/usuarios/login'
	template_name = 'perfil.html'

	def process_request(self, request, *args, **kwargs):
		return render(request, self.template_name, 
			{'nombre' : request.user.first_name + ' ' + request.user.last_name})

	def get(self, request, *args, **kwargs):
		return self.process_request(request, args, kwargs)

	def post(self, request, *args, **kwargs):
		return self.process_request(request, args, kwargs)


class PasswordRecoverView(TemplateView):
	template_name = 'recuperacion.html'


