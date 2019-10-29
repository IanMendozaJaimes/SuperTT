from django.shortcuts import render, redirect
from django.contrib.auth import login, logout
from django.views.generic import TemplateView
from django.contrib.auth.hashers import check_password, make_password
from django.contrib.auth.mixins import LoginRequiredMixin
from django.conf import settings
from django.http import JsonResponse
from general.util import *

import os
import hashlib
import random


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
				if user.is_active:
					login(request, user)
					return redirect('/proyectos/todos')
				else:
					m = Message()
					m.add_validation_error(USER_IS_NOT_ACTIVE, 'baduser')
					e = m.get_messages()
					e.update({
						'correo': request.POST['correo'],
					})
					return render(request, self.template_name, e)
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

		if not v.field(data['apellidos']):
			m.add_validation_error(REQUIRED_FIELD, 'apellidos')
		elif not v.name(data['apellidos']):
			m.add_validation_error(INVALID_NAME, 'apellidos')

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
							first_name=request.POST['nombre'],
							last_name=request.POST['apellidos'])
			new_user.save()
			m = Message()
			m.add_alert(SEND_EMAIL, 'Tu cuenta se registro exitosamente!')
			request.session['resp'] = m.get_messages()

			token = Token.objects.get(user=new_user.id).key

			h = hashlib.sha1(request.POST['correo'].encode('utf-8')).hexdigest()
			uh = UserHashes(user=new_user, hash=h, proposito=VALIDATE)
			uh.save()

			url = settings.SITE_URL + 'usuarios/validar?token=' + h

			e = Email()
			e.send_validation_email(request.POST['correo'],request.POST['nombre'],url)
			e.close()

			return redirect('/usuarios/login')
		else:
			e = err.get_messages()
			e.update({
				'nombre': request.POST['nombre'],
				'correo': request.POST['correo'],
				'apellidos': request.POST['apellidos'],
			})
			return render(request, self.template_name, e)




class ProfileView(LoginRequiredMixin, TemplateView):
	login_url = '/usuarios/login'
	template_name = 'perfil.html'

	def process_request(self, request, *args, **kwargs):
		opciones = Estudios.objects.all();
		return render(request, self.template_name, 
			{
				'nombre': request.user.first_name,
				'apellidos': request.user.last_name,
				'correo': request.user.email,
				'avatar': 'imgUsuario/'+request.user.imagen_perfil,
				'opciones': opciones,
				'op_selected': request.user.estudios_id,
			})

	def get(self, request, *args, **kwargs):
		return self.process_request(request, args, kwargs)

	def post(self, request, *args, **kwargs):
		return self.process_request(request, args, kwargs)



class PasswordRecoverView(TemplateView):
	template_name = 'recuperacion.html'



class CambiarCView(TemplateView):
	template_name = 'cambiar_contra.html'

	def get(self, request, *args, **kwargs):
		if 'erecuperacion' in request.session:
			return render(request, self.template_name)
		else:
			return redirect('/usuarios/login')


	def post(self, request, *args, **kwargs):
		if 'erecuperacion' in request.session:
			v = Validator()
			m = Message()

			data = request.POST

			if not v.field(data['contra']):
				m.add_validation_error(REQUIRED_FIELD, 'contra')

			if not v.field(data['contraDos']):
				m.add_validation_error(REQUIRED_FIELD, 'contraDos')

			if not v.are_the_same(data['contra'], data['contraDos']):
				m.add_validation_error(PASSWORDS_ARE_NOT_THE_SAME, 'contraDos')

			if m.get_len() == 0:
				User.objects.filter(email=request.session['erecuperacion']).update(password=make_password(data['contra']))
				m = Message()
				m.add_alert(PASSWORD_CHANGED, 'Éxito!')
				request.session['resp'] = m.get_messages()
				del request.session['erecuperacion']
				
				return redirect('/usuarios/login')
			else:
				return render(request, self.template_name, m.get_messages())

		else:
			return redirect('/usuarios/login')




def CambiarUsuarioView(request):
	v = Validator()
	m = Message()
	nom = request.GET['nombre']
	apellidos = request.GET['apellidos']
	opcion = request.GET['estudios']
	correo = request.user.email

	if not v.field(nom):
		m.add_validation_error(REQUIRED_FIELD, 'nombre')
	elif not v.name(nom):
		m.add_validation_error(INVALID_NAME, 'nombre')

	if not v.field(apellidos):
		m.add_validation_error(REQUIRED_FIELD, 'apellidos')
	elif not v.name(apellidos):
		m.add_validation_error(INVALID_NAME, 'apellidos')

	if m.get_len() == 0:
		User.objects.filter(email=correo).update(first_name=nom,
												 last_name=apellidos, 
												 estudios=int(opcion))
		return JsonResponse({'err':{}})

	return JsonResponse({'err':m.get_messages()})



def CambiarContraView(request):
	v = Validator()
	m = Message()

	contra = request.GET['contra']
	contraDos = request.GET['contraDos']
	correo = request.user.email

	if not v.field(contra):
		m.add_validation_error(REQUIRED_FIELD, 'contra')

	if not v.field(contraDos):
		m.add_validation_error(REQUIRED_FIELD, 'contraDos')

	if not v.are_the_same(contra, contraDos):
		m.add_validation_error(PASSWORDS_ARE_NOT_THE_SAME, 'contraDos')


	if m.get_len() == 0:
		contra = make_password(contra)
		User.objects.filter(email=correo).update(password=contra)
		user = User.objects.get(email=correo)
		login(request, user)
		return JsonResponse({'err':{}})

	return JsonResponse({'err':m.get_messages()})



def CambiarFotoView(request):

	def get_tipo_archivo(archivo):
		if '.png' in archivo:
			return '.png'
		elif ('.jpg' in archivo) or ('.jpeg' in archivo):
			return '.jpg'
		else:
			return None

	try:
		email = request.user.email
		old = request.user.imagen_perfil
		f = request.FILES['foto']
		tipo = get_tipo_archivo(f.name)
		if tipo is None:
			return JsonResponse({'err':{'text':'Archivo no soportado'}})
		name = hashlib.sha1(f.name.encode('utf-8')).hexdigest() + tipo
		print(name)
		with open(settings.MEDIA_ROOT+'/imgUsuario/' + name, 'wb+') as file:
			for x in f.chunks():
				file.write(x)

		User.objects.filter(email=email).update(imagen_perfil=name)

		if old != settings.IMG_DEFAULT:
			os.remove(settings.MEDIA_ROOT+'/imgUsuario/'+old)

		return JsonResponse({'err':{}, 'url_imagen':settings.MEDIA_URL+'imgUsuario/'+name})
	except Exception as e:
		return JsonResponse({'err':{
			'text': 'No fue posible subir la imagen.'
		}})


def ValidarUsuarioView(request):
	try:
		token = request.GET['token']
		uh = UserHashes.objects.get(hash=token)
		
		user = User.objects.get(email=uh.user.email)
		user.is_active = True
		user.save()
		
		uh.delete()

		m = Message()
		m.add_alert(USER_HAS_BEEN_VALIDATED, 'Éxito!')
		request.session['resp'] = m.get_messages()
		
		return redirect('/usuarios/login')
	except:
		return redirect('/usuarios/login')



def EnviarCorreoRecuperacionView(request):
	try:
		email = request.POST['correo']
		user = User.objects.get(email=email)
		hemail = email + str(random.randrange(0,1000))
		
		h = hashlib.sha1(hemail.encode('utf-8')).hexdigest()
		
		uh = UserHashes(user=user, hash=h, proposito=CHANGE_PASSWORD)
		uh.save()

		url = settings.SITE_URL + 'usuarios/contrasena?token=' + h

		e = Email()
		e.send_change_password_email(email, user.first_name, url)
		e.close()

		m = Message()
		m.add_alert(SEND_EMAIL_PASSWORD, 'Éxito!')
		request.session['resp'] = m.get_messages()

	except Exception as e:
		print('Ocurrio un error:', e)
	finally:
		return redirect('/usuarios/login')


def RecuperarContraView(request):
	try:
		token = request.GET['token']
		uh = UserHashes.objects.get(hash=token)
		request.session['erecuperacion'] = uh.user.email
		uh.delete()
		return redirect('/usuarios/cambiarC')
	except Exception as e:
		print('Un error:', e)
		return redirect('/usuarios/login')


def LogoutView(request):
	logout(request)
	return redirect('/usuarios/login')









