# Generated by Django 2.2.5 on 2019-10-20 23:43

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('proyectos', '0003_traduccion_fechacreacion'),
    ]

    operations = [
        migrations.AlterField(
            model_name='traduccion',
            name='nombre',
            field=models.DateTimeField(auto_now_add=True),
        ),
    ]
