from django.test import TestCase
from proyectos.views import ProyectsView
from django.urls import reverse
from users.models import User, UserHashes, Estudios
from proyectos.models import Proyecto, Traduccion
from general.util import CHANGE_PASSWORD, VALIDATE

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

class RecuperarContraViewTest(TestCase):
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

class EnviarCorreoRecuperacionView(TestCase):
    def setUp(self):
        self.user = User.objects.create_user(email="hola@gmail.com", password="hola")

    def test_enviar_correo_error(self):
        url = reverse("enviar_correo_recuperacion_view")
        response = self.client.post(url)
        self.assertEqual(response.status_code, 302)

    def test_enviar_correo(self):
        url = reverse("enviar_correo_recuperacion_view")
        response = self.client.post(url, {"correo":"hola@gmail.com"})
        self.assertTrue(self.client.session["resp"] is not None)

class ValidarUsuarioViewTest(TestCase):
    def setUp(self):
        user = User.objects.create_user(email="validar_usuario_view@gmail.com", password="hola")
        user_hash = UserHashes.objects.create(user=user, hash="token", proposito=VALIDATE)

    def test_validar_usuario(self):
        url = reverse("validar_usuario_view")
        response = self.client.get(url, {"token":"token"})
        self.assertTrue(self.client.session["resp"] is not None)
        

    def test_validar_usuario_error(self):
        url = reverse("validar_usuario_view")
        response = self.client.get(url)
        self.assertEqual(response.status_code, 302)

class SignUpViewTest(TestCase):
    def setUp(self):
        user = User.objects.create_user(email="usuario@gmail.com", password="hola")

    def test_get_response(self):
        url = reverse("sign_up_view")
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)
    
    def test_post_response_exito(self):
        url = reverse("sign_up_view")
        response = self.client.post(url, {"correo": "correcto@gmail.com", 
            "contra":"correcto", "contraConf":"correcto", "nombre":"nombre", "apellidos":"apellidos"})
        self.assertTrue("Verifique" in self.client.session["resp"].get("messages")[0].get("text"))

    def test_post_response_campos_requeridos_error(self):
        url = reverse("sign_up_view")
        response = self.client.post(url, {"correo": "", 
            "contra":"", "contraConf":"", "nombre":"", "apellidos":""})
        self.assertEqual(response.status_code, 200)

    def test_post_response_max_longitud_campos_error(self):
        url = reverse("sign_up_view")
        response = self.client.post(url, {"correo": "correcto@gmail.com"*10, 
            "contra":"correcto"*10, "contraConf":"correcto"*10, "nombre":"nombre"*10, "apellidos":"apellidos"*10})
        self.assertEqual(response.status_code, 200)

    def test_post_response_min_longitud_campos_error(self):
        url = reverse("sign_up_view")
        response = self.client.post(url, {"correo": "correcto@gmail.com", 
            "contra":"a", "contraConf":"a", "nombre":"nombre", "apellidos":"apellidos"})
        self.assertEqual(response.status_code, 200)

    def test_post_response_diferente_pwd_error(self):
        url = reverse("sign_up_view")
        response = self.client.post(url, {"correo": "correcto@gmail.com", 
            "contra":"contracontra", "contraConf":"contra", "nombre":"nombre", "apellidos":"apellidos"})
        self.assertEqual(response.status_code, 200)

    def test_post_response_campos_invalido_error(self):
        url = reverse("sign_up_view")
        response = self.client.post(url, {"correo": "incorrectogmail.com", 
            "contra":"correcto", "contraConf":"correcto", "nombre":"nombre777", "apellidos":"apellidos777"})
        self.assertEqual(response.status_code, 200)

    def test_post_response_usuario_existente_error(self):
        url = reverse("sign_up_view")
        response = self.client.post(url, {"correo": "usuario@gmail.com", 
            "contra":"correcto", "contraConf":"correcto", "nombre":"nombre", "apellidos":"apellidos"})
        self.assertEqual(response.status_code, 200)

class ProfileViewTest(TestCase):
    def setUp(self):
        user = User.objects.create_user(email="profile@gmail.com", password="hola", is_active=True)
        self.client.login(email="profile@gmail.com", password="hola")

    def test_get_response(self):
        url = reverse("profile_view")
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)

    def test_post_response(self):
        url = reverse("profile_view")
        response = self.client.post(url)
        self.assertEqual(response.status_code, 200)

class CambiarUsuarioViewTest(TestCase):
    def setUp(self):
        Estudios.objects.create(grado="Licenciatura")
        user = User.objects.create_user(email="cambiar@gmail.com", password="cambiar", is_active=True)
        self.client.login(email="cambiar@gmail.com", password="cambiar")

    def test_response(self):
        url = reverse("cambiar_usuario_view")
        response = self.client.get(url, {"nombre":"nombre", "apellidos":"apellidos", "estudios":"1"})
        self.assertTrue(response.content.decode("utf-8") == '{"err": {}}')

    def test_response_campos_requeridos_error(self):
        url = reverse("cambiar_usuario_view")
        response = self.client.get(url, {"nombre":"", "apellidos":"", "estudios":"1"})
        self.assertFalse(response.content.decode("utf-8") == '{"err": {}}')

    def test_response_campos_invalidos_error(self):
        url = reverse("cambiar_usuario_view")
        response = self.client.get(url, {"nombre":"nombre777", "apellidos":"apellidos777", "estudios":"1"})
        self.assertFalse(response.content.decode("utf-8") == '{"err": {}}')

    def test_response_max_longitud_error(self):
        url = reverse("cambiar_usuario_view")
        response = self.client.get(url, {"nombre":"nombre"*10, "apellidos":"apellidos"*10, "estudios":"1"})
        self.assertFalse(response.content.decode("utf-8") == '{"err": {}}')


class CambiarContraViewTest(TestCase):
    def setUp(self):
        user = User.objects.create_user(email="cambiar@gmail.com", password="cambiar", is_active=True)
        self.client.login(email="cambiar@gmail.com", password="cambiar")

    def test_response(self):
        url = reverse("cambiar_contra_request")
        response = self.client.get(url, {"contra":"nueva_contra", "contraDos":"nueva_contra"})
        self.assertTrue(response.content.decode("utf-8") == '{"err": {}}')

    def test_response_campo_requirido_error(self):
        url = reverse("cambiar_contra_request")
        response = self.client.get(url, {"contra":"", "contraDos":""})
        self.assertFalse(response.content.decode("utf-8") == '{"err": {}}')

    def test_response_max_longitud_error(self):
        url = reverse("cambiar_contra_request")
        response = self.client.get(url, {"contra":"nueva_contra"*10, "contraDos":"nueva_contra"*10})
        self.assertFalse(response.content.decode("utf-8") == '{"err": {}}')

    def test_response_min_longitud_error(self):
        url = reverse("cambiar_contra_request")
        response = self.client.get(url, {"contra":"n", "contraDos":"n"})
        self.assertFalse(response.content.decode("utf-8") == '{"err": {}}')

    def test_response_diferente_contra_error(self):
        url = reverse("cambiar_contra_request")
        response = self.client.get(url, {"contra":"diferente_contra", "contraDos":"nueva_contra"})
        self.assertFalse(response.content.decode("utf-8") == '{"err": {}}')

class CambiarCViewTest(TestCase):
    def setUp(self):
        user = User.objects.create_user(email="cambiar@gmail.com", password="cambiar", is_active=True)
        self.client.login(email="cambiar@gmail.com", password="cambiar")

    def test_get_response(self):
        url = reverse("cambiar_contra_view")
        response = self.client.get(url)
        self.assertEqual(response.status_code, 302)

    def test_get_response_session(self):
        url = reverse("cambiar_contra_view")
        sesion = self.client.session
        sesion["erecuperacion"] = True
        sesion.save()
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)

    def test_post_response(self):
        url = reverse("cambiar_contra_view")
        response = self.client.post(url)
        self.assertEqual(response.status_code, 302)

    def test_post_response_exito(self):
        url = reverse("cambiar_contra_view")
        sesion = self.client.session
        sesion["erecuperacion"] = True
        sesion.save()
        response = self.client.post(url, {"contra":"nueva_contra", "contraDos":"nueva_contra"})
        self.assertEqual(response.status_code, 302)

    def test_post_esponse_campo_requirido_error(self):
        url = reverse("cambiar_contra_view")
        sesion = self.client.session
        sesion["erecuperacion"] = True
        sesion.save()
        response = self.client.post(url, {"contra":"", "contraDos":""})
        self.assertEqual(response.status_code, 200)

    def test_post_response_max_longitud_error(self):
        url = reverse("cambiar_contra_view")
        sesion = self.client.session
        sesion["erecuperacion"] = True
        sesion.save()
        response = self.client.post(url, {"contra":"nueva_contra"*10, "contraDos":"nueva_contra"*10})
        self.assertEqual(response.status_code, 200)

    def test_post_response_min_longitud_error(self):
        url = reverse("cambiar_contra_view")
        sesion = self.client.session
        sesion["erecuperacion"] = True
        sesion.save()
        response = self.client.post(url, {"contra":"n", "contraDos":"n"})
        self.assertEqual(response.status_code, 200)

    def test_post_response_diferente_contra_error(self):
        url = reverse("cambiar_contra_view")
        sesion = self.client.session
        sesion["erecuperacion"] = True
        sesion.save()
        response = self.client.post(url, {"contra":"diferente_contra", "contraDos":"nueva_contra"})
        self.assertEqual(response.status_code, 200)

class ProyectsViewTest(TestCase):
    def setUp(self):
        user = User.objects.create_user(email="cambiar@gmail.com", password="cambiar", is_active=True)
        self.client.login(email="cambiar@gmail.com", password="cambiar")
        proyecto = Proyecto.objects.create(usuario=user, nombre="Proyecto1", calificacion=0)
        Traduccion.objects.create(proyecto = proyecto, usuario=user, archivo="" , calificacion=0.0, traduccion="")
        Proyecto.objects.create(usuario=user, nombre="Proyecto 2", calificacion=0)

    def test_get_response(self):
        url = reverse("proyectos_todos")
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)

    def test_post_response(self):
        url = reverse("proyectos_todos")
        response = self.client.post(url)
        self.assertEqual(response.status_code, 200)

class TranslationsViewTest(TestCase):
    def setUp(self):
        user = User.objects.create_user(email="cambiar@gmail.com", password="cambiar", is_active=True)
        self.client.login(email="cambiar@gmail.com", password="cambiar")
        self.proyecto = Proyecto.objects.create(usuario=user, nombre="Proyecto1", calificacion=0)
        Traduccion.objects.create(proyecto = self.proyecto, usuario =user, archivo="" , calificacion=0.0, traduccion="")

    def test_get_response(self):
        url = reverse("traducciones_list_view")
        response = self.client.get(url, {"id": str(self.proyecto.id)})
        self.assertEqual(response.status_code, 200)

    def test_post_response(self):
        url = reverse("traducciones_list_view")
        response = self.client.post(url)
        self.assertEqual(response.status_code, 302)

class CrearProyectoViewTest(TestCase):
    def setUp(self):
        user = User.objects.create_user(email="cambiar@gmail.com", password="cambiar", is_active=True)
        self.client.login(email="cambiar@gmail.com", password="cambiar")
        Proyecto.objects.create(usuario=user, nombre="Proyecto", calificacion=0)

    def test_get_response(self):
        url = reverse("crear_proyecto_view")
        response = self.client.get(url, {"nombre": "Proyecto 1"})
        self.assertTrue("{}" in response.content.decode("utf-8"))

    def test_get_response_proyecto_existe_error(self):
        url = reverse("crear_proyecto_view")
        response = self.client.get(url, {"nombre": "Proyecto"})
        self.assertFalse("{}" in response.content.decode("utf-8"))

    def test_get_response_nombre_invalido_error(self):
        url = reverse("crear_proyecto_view")
        response = self.client.get(url, {"nombre": ""})
        self.assertFalse("{}" in response.content.decode("utf-8"))

class CambiarProyectoViewTest(TestCase):
    def setUp(self):
        user = User.objects.create_user(email="usuario@gmail.com", password="cambiar", is_active=True)
        self.client.login(email="usuario@gmail.com", password="cambiar")
        Proyecto.objects.create(usuario=user, nombre="Proyecto", calificacion=0)
        Proyecto.objects.create(usuario=user, nombre="Proyecto prueba", calificacion=0)

    def test_get_response(self):
        url = reverse("cambiar_proyecto_view")
        response = self.client.get(url, {"oldnombre": "Proyecto", "nombre": "Nuevo nombre"})
        self.assertTrue("{}" in response.content.decode("utf-8"))

    def test_get_response_nombre_invalido_error(self):
        url = reverse("cambiar_proyecto_view")
        response = self.client.get(url, {"oldnombre": "Proyecto", "nombre": ""})
        self.assertFalse("{}" in response.content.decode("utf-8"))

    def test_get_response_proyecto_existe_error(self):
        url = reverse("cambiar_proyecto_view")
        response = self.client.get(url, {"oldnombre": "Proyecto", "nombre": "Proyecto prueba"})
        self.assertFalse("{}" in response.content.decode("utf-8"))

class EliminarProyectoViewTest(TestCase):
    def setUp(self):
        user = User.objects.create_user(email="usuario@gmail.com", password="cambiar", is_active=True)
        self.client.login(email="usuario@gmail.com", password="cambiar")
        self.proyecto = Proyecto.objects.create(usuario=user, nombre="Proyecto", calificacion=0)

    def test_get_response(self):
        url = reverse("eliminar_proyecto_view")
        response = self.client.get(url, {"id": str(self.proyecto.id)})
        self.assertTrue("{}" in response.content.decode("utf-8"))

class ActualizarTraduccion(TestCase):
    def setUp(self):
        user = User.objects.create_user(email="cambiar@gmail.com", password="cambiar", is_active=True)
        self.client.login(email="cambiar@gmail.com", password="cambiar")
        self.proyecto = Proyecto.objects.create(usuario=user, nombre="Proyecto1", calificacion=0)
        self.traduccion = Traduccion.objects.create(proyecto = self.proyecto, 
            usuario =user, archivo="" , calificacion=0.0, traduccion="")
        user_error = User.objects.create_user(email="usuario@gmail.com", password="usuario", is_active=True)
        proyecto_error = Proyecto.objects.create(usuario=user_error, nombre="Proyecto1", calificacion=0)
        self.traduccion_error = Traduccion.objects.create(proyecto = proyecto_error, 
            usuario=user_error, archivo="" , calificacion=0.0, traduccion="")

    def test_get_response(self):
        url = reverse("actualizar_traduccion_view")
        response = self.client.get(url, {"id": str(self.traduccion.id), "grade": str(5)})
        self.assertTrue("{}" in response.content.decode("utf-8"))

    def test_get_response_traduccion_error(self):
        url = reverse("actualizar_traduccion_view")
        response = self.client.get(url, {"id": str(self.traduccion_error.id), "grade": str(5)})
        self.assertFalse("{}" in response.content.decode("utf-8"))

    def test_get_response_error(self):
        url = reverse("actualizar_traduccion_view")
        response = self.client.get(url, {"id": str(99), "grade": str(5)})
        self.assertFalse("{}" in response.content.decode("utf-8"))

