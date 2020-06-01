from django.test import TestCase
from general.util import change_time_zone, Message, ImageUtil, Validator, Email
from django.core.exceptions import ObjectDoesNotExist
from django.utils import timezone
from users.models import User
from proyectos.models import Proyecto

class ChangeTimeZoneTest(TestCase):
    def test_change_time_zone(self):
        data = dict()
        data["fechaCreacion"] = timezone.now()
        data["fechaModificacion"] = timezone.now()
        self.assertTrue(change_time_zone(data) is not None)

class MessageTest(TestCase):
    def test_add_alert(self):
        message = Message()
        message.add_alert(text = "Un mensaje")
        self.assertEquals(message.get_len(), 1)

class ImageUtilTest(TestCase):
    def test_init(self):
        image_util = ImageUtil()
        self.assertEquals(image_util.build_url(1, 1, "imagen"), 
            "http://192.168.1.68:8000/media/proyectos/1/1/imagen")

class ValidatorTest(TestCase):
    def setUp(self):
        self.validator = Validator()
        self.user = User.objects.create_user(email="hola@gmail.com", password="hola")
        proyecto = Proyecto.objects.create(usuario=self.user, nombre="Proyecto 1", calificacion=0)

    def test_are_the_same_true(self):
        self.assertTrue(self.validator.are_the_same(1, 1))

    def test_are_the_same_false(self):
        self.assertFalse(self.validator.are_the_same(1, 2))

    def test_field(self):
        self.assertFalse(self.validator.field(None))

    def test_user_exists_true(self):
        self.assertTrue(self.validator.user_exists("hola@gmail.com"))

    def test_user_exists_false(self):
        self.assertFalse(self.validator.user_exists("error@gmail.com"))

    def test_email(self):
        self.assertFalse(self.validator.email(" "))

    def test_min_len_true(self):
        self.assertTrue(self.validator.min_len("contrasegura"))

    def test_min_len_false(self):
        self.assertFalse(self.validator.min_len("error"))

    def test_max_len_false(self):
        self.assertFalse(self.validator.max_len("contrasegura"*20))

    def test_max_len_true(self):
        self.assertTrue(self.validator.max_len("error"))

    def test_name_true(self):
        self.assertTrue(self.validator.name("Carlos Tonatihu"))

    def test_name_false(self):
        self.assertFalse(self.validator.name("Tona777"))

    def test_proyect_exists_true(self):
        self.assertTrue(self.validator.proyect_exists(self.user, "Proyecto 1"))

    def test_proyect_exists_false(self):
        self.assertFalse(self.validator.proyect_exists(self.user, "Proyecto 2"))


class EmailTest(TestCase):
    def setUp(self):
        self.email_test = Email()

    def tearDown(self):
        self.email_test.close()

    def test_close(self):
        email = Email()
        email.close()

    def test_send_validation_email(self):
        self.email_test.send_validation_email("calorstonatihu@gmail.com", "tona", 
            "localhost:8080/login")
        self.assertTrue(True)

    def test_send_change_password_email(self):
        self.email_test.send_change_password_email("calorstonatihu@gmail.com", "tona", 
            "localhost:8080/login")
        self.assertTrue(True)