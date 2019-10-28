import pytz
import re
import smtplib

from django.conf import settings
from django.core.exceptions import ObjectDoesNotExist
from django.utils import timezone
from datetime import datetime

from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

from users.models import *
from proyectos.models import *



# Coinciden con la documentacion
USER_IS_NOT_ACTIVE = """La cuenta aún no ha sido verificada."""

EMAIL_ALREADY_TAKEN = """El correo electrónico ya se ha utilizado."""

SEND_EMAIL = """Verifique su cuenta a través del correo electrónico que se le ha mandado."""

SEND_EMAIL_PASSWORD = """Se ha enviado un correo electrónico para la recuperación de su contraseña."""


# No coinciden con la documentacion
REQUIRED_FIELD = """Por favor, rellena este campo."""

PASSWORDS_ARE_NOT_THE_SAME = """Las contraseñas no coinciden."""

PROYECT_ALREADY_EXISTS = """Ya existe un proyecto con ese nombre."""

USER_HAS_BEEN_VALIDATED = """Tu cuenta ha sido validada. Ahora puede iniciar sesión."""

PASSWORD_CHANGED = """La contraseña ha sido cambiada. Ahora puede iniciar sesión normalmente con su nueva contraseña."""

VALIDATE = 1

CHANGE_PASSWORD = 2


# En desacuerdo con la documentacion
INVALID_LOGIN = """El correo o la contraseña son incorrectos."""

INVALID_NAME = """El nombre solo puede contener caracteres alfabéticos."""

INVALID_EMAIL = """El correo ingresado no es valido."""


class ImageUtil:
	def __init__(self):
		self.pre = ""
		if settings.SITE_URL[-1] != "/":
			self.pre = "/"
		self.relative_path = self.pre + "media/proyectos/"
	def build_url(self, idUser, idProj, nameFile):
		path = settings.SITE_URL + self.relative_path + str(idUser) + "/" + str(idProj) + "/" + str(nameFile)
		
		print(path)
		return path
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



class Email():

	def __init__(self):
		self.s = smtplib.SMTP(host=settings.SMTP_HOST, port=settings.SMTP_PORT)
		self.s.ehlo()
		self.s.starttls()
		self.s.ehlo()
		self.s.login(settings.SMTP_USER, settings.SMTP_PASSWORD)


	def close(self):
		self.s.quit()


	def get_template(self, template):
		tem = ''
		
		with open(template, 'r') as t:
			tem = t.read()

		return tem

	def send_validation_email(self, addressee, aname, link):
		msg = MIMEMultipart()
		msg['From'] = settings.SMTP_USER
		msg['To'] = addressee
		msg['Subject'] = 'Validación de su cuenta.'
		
		message = self.get_template(settings.BASE_DIR+'/general/validation_message.html')
		message = message.replace('PERSON_NAME', aname)
		message = message.replace('LINK', link)
		msg.attach(MIMEText(message, 'html'))

		self.s.send_message(msg)


	def send_change_password_email(self, addressee, aname, link):
		msg = MIMEMultipart()
		msg['From'] = settings.SMTP_USER
		msg['To'] = addressee
		msg['Subject'] = 'Recuperación de contraseña.'
		
		message = self.get_template(settings.BASE_DIR+'/general/change_password.html')
		message = message.replace('PERSON_NAME', aname)
		message = message.replace('LINK', link)
		msg.attach(MIMEText(message, 'html'))

		self.s.send_message(msg)



def change_time_zone(data):
    tz = pytz.timezone('America/Mexico_City')
    
    t = timezone.localtime(data['fechaCreacion'], tz)
    data['fechaCreacion'] = datetime(t.year, t.month, t.day, 
        t.hour, t.minute, t.second, t.microsecond)
    
    t = timezone.localtime(data['fechaModificacion'], tz)
    data['fechaModificacion'] = datetime(t.year, t.month, t.day, 
        t.hour, t.minute, t.second, t.microsecond)

    return data




