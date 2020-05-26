from django.test import TestCase
from general.util import change_time_zone, Message, ImageUtil
from django.utils import timezone

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