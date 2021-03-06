# Generated by Django 2.2.5 on 2019-10-18 18:08

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('users', '0002_auto_20191017_0008'),
    ]

    operations = [
        migrations.AlterField(
            model_name='user',
            name='imagen_perfil',
            field=models.TextField(default='avatarDefault.png'),
        ),
        migrations.CreateModel(
            name='UserHashes',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('hash', models.CharField(max_length=200)),
                ('proposito', models.IntegerField()),
                ('user', models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]
