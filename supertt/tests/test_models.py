from django.test import TestCase
from users.models import User

class UserManagerTest(TestCase):
    def test_create_user(self):
        user = User.objects.create_user(email="hola@gmail.com", password="hola")
        user.save()
        self.assertEqual(user.email, "hola@gmail.com")

    def test_create_user_not_email(self):
        with self.assertRaises(ValueError):
            user = User.objects.create_user(email=None, password="hola")

    def test_create_superuser_is_staff_false(self):
        with self.assertRaises(ValueError):
            user = User.objects.create_superuser(email="hola", password="hola", is_staff=False)
    
    def test_create_superuser_is_superuser_false(self):
        with self.assertRaises(ValueError):
            user = User.objects.create_superuser(email="hola", password="hola", is_superuser=False)

    def test_create_superuser(self):
        user = User.objects.create_superuser(email="hola@gmail.com", password="hola")
        user.save()
        self.assertEqual(user.email, "hola@gmail.com")
