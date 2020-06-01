from django.test import TestCase
from proyectos.views import ProyectsView
from django.urls import reverse
from users.models import User, UserHashes
from general.util import CHANGE_PASSWORD

class LoginViewTest(TestCase):
    def test_get_response(self):
        url = reverse("login_view")
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)

    def test_get_response_session(self):
        user = User.objects.create_user(email="hola@gmail.com", password="hola", is_active=True)
        self.client.login(email="hola@gmail.com", password="hola")
        session = self.client.session
        session["resp"] = {'nMessages':0, 'messages':[]}
        session.save()
        url = reverse("login_view")
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)

    def test_post_response_is_active_false(self):
        user = User.objects.create_user(email="hola@gmail.com", password="hola", is_active=False)
        url = reverse("login_view")
        response = self.client.post(url, {"correo": "hola@gmail.com", "contra":"hola"})
        self.assertEqual(response.status_code, 200)
        

    def test_post_response_is_active_true(self):
        user = User.objects.create_user(email="hola@gmail.com", password="hola", is_active=True)
        url = reverse("login_view")
        response = self.client.post(url, {"correo": "hola@gmail.com", "contra":"hola"})
        self.assertEqual(response.status_code, 302)


    def test_post_response_user_none(self):
        url = reverse("login_view")
        response = self.client.post(url, {"correo": "none@gmail.com", "contra":"hola"})
        self.assertEqual(response.status_code, 200)

    def test_post_response_invalid_data(self):
        url = reverse("login_view")
        response = self.client.post(url, {"correo": "", "contra":""})
        self.assertEqual(response.status_code, 200)

    def test_post_response_valid_email(self):
        url = reverse("login_view")
        response = self.client.post(url, {"correo": "gaga", "contra":""})
        self.assertEqual(response.status_code, 200)

    def test_post_response_authenticate(self):
        user = User.objects.create_user(email="hola@gmail.com", password="hola")
        url = reverse("login_view")
        response = self.client.post(url, {"correo": "hola@gmail.com", "contra":"error"})
        self.assertEqual(response.status_code, 200)
        
class LogoutViewTest(TestCase):
    def test_logout(self):
        url = reverse("logout_view")
        response = self.client.get(url)
        self.assertEqual(response.status_code, 302)

class RecuperarContraView(TestCase):
    def test_recuperar_contra(self):
        user = User.objects.create_user(email="hola@gmail.com", password="hola")
        user_hash = UserHashes.objects.create(user=user, hash="token", proposito=CHANGE_PASSWORD)
        url = reverse("recuperar_contra_view")
        response = self.client.get(url, {"token": "token"})
        self.assertEqual(response.status_code, 302)

    def test_recuperar_contra_exception(self):
        user = User.objects.create_user(email="hola@gmail.com", password="hola")
        user_hash = UserHashes.objects.create(user=user, hash="token", proposito=CHANGE_PASSWORD)
        url = reverse("recuperar_contra_view")
        response = self.client.get(url, {"token": "error"})
        self.assertEqual(response.status_code, 302)