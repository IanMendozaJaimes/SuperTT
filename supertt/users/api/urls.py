from django.urls import path
from users.api.views import registration_view

app_name = 'users'

url_patterns = [
    path('register', registration_view),
]


def get_user_urls():
	return [
		path('users/register', registration_view),
	]