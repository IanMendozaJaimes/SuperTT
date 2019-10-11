
SEND_EMAIL = """Para concluir el registro, por favor verifica tu cuenta a través del mensaje
				que hemos enviado a tu correo."""

REQUIRED_FIELD = """Por favor, rellena este campo."""

INVALID_NAME = """El nombre solo puede contener caracteres alfabéticos."""

INVALID_EMAIL = """El correo ingresado no es valido."""

EMAIL_ALREADY_TAKEN = """El correo introducido ya está en uso."""

PASSWORDS_ARE_NOT_THE_SAME = """Las contraseñas no coinciden."""

INVALID_LOGIN = """El correo o la contraseña son incorrectos."""


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


