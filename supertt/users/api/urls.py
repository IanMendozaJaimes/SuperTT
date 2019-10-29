from django.urls import path
from users.api.views import registration_view, edit_user_view, login_view, recover_password_view
from rest_framework.authtoken.views import obtain_auth_token

app_name = 'users'

url_patterns = [
    path('register', registration_view),
]


def get_user_urls():
	return [
		path('users', registration_view),
		path('users/login', login_view),
		path('users/<idUsuario>', edit_user_view),
		path('recover', recover_password_view)
		#recover password
	]