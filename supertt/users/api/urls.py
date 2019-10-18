from django.urls import path
from users.api.views import registration_view
from rest_framework.authtoken.views import obtain_auth_token

app_name = 'users'

url_patterns = [
    path('register', registration_view),
]


def get_user_urls():
	return [
		path('users', registration_view),
		path('users/login', obtain_auth_token),
	]