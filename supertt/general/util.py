import pytz
import re

from django.core.exceptions import ObjectDoesNotExist
from django.utils import timezone
from datetime import datetime

from users.models import *
from proyectos.models import *


SEND_EMAIL = """Para concluir el registro, por favor verifica tu cuenta a través del mensaje
				que hemos enviado a tu correo."""

REQUIRED_FIELD = """Por favor, rellena este campo."""

INVALID_NAME = """El nombre solo puede contener caracteres alfabéticos."""

INVALID_EMAIL = """El correo ingresado no es valido."""

EMAIL_ALREADY_TAKEN = """El correo introducido ya está en uso."""

PASSWORDS_ARE_NOT_THE_SAME = """Las contraseñas no coinciden."""

INVALID_LOGIN = """El correo o la contraseña son incorrectos."""

PROYECT_ALREADY_EXISTS = """Ya existe un proyecto con ese nombre."""


class Message():

	def __init__(self):
		self.messages = {'nMessages':0, 'messages':[]}
		self.counter = 0

	
	def get_messages(self):
		return self.messages


	def get_len(self):
		return self.counter


	def add_alert(self, text, header='', btn='Aceptar'):
		self.counter += 1
		self.messages['messages'].append({
			'id': self.counter,
			'type': 'alert',
			'text': text,
			'header': header,
			'btn': btn,
		})
		self.messages['nMessages'] = self.counter


	def add_validation_error(self, text, name):
		self.counter += 1
		self.messages['messages'].append({
			'id': self.counter,
			'type': 'validation_error',
			'text': text,
			'name': name,
		})
		self.messages['nMessages'] = self.counter



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

	def proyect_exists(self, user, proyect_name):
		try:
			p = Proyecto.objects.get(usuario=user, nombre=proyect_name)
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



def change_time_zone(data):
    tz = pytz.timezone('America/Mexico_City')
    
    t = timezone.localtime(data['fechaCreacion'], tz)
    data['fechaCreacion'] = datetime(t.year, t.month, t.day, 
        t.hour, t.minute, t.second, t.microsecond)
    
    t = timezone.localtime(data['fechaModificacion'], tz)
    data['fechaModificacion'] = datetime(t.year, t.month, t.day, 
        t.hour, t.minute, t.second, t.microsecond)

    return data




