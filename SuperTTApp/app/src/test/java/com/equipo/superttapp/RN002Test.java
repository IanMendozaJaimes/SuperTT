package com.equipo.superttapp;

import com.equipo.superttapp.util.RN002;

import org.junit.Assert;
import org.junit.Test;

public class RN002Test {
    @Test
    public void isEmailValid_validEmail_shouldReturnTrue() {
        Assert.assertTrue("Prrobando que se regrese true en una email valido",
                RN002.isEmailValid("carlostonatihu@gmail.com"));
    }

    @Test
    public void isEmailValid_invalidEmail_shouldReturnFalse() {
        Assert.assertFalse("Prrobando que se regrese false en una email no valido",
                RN002.isEmailValid("carlostonatihumail.com"));
    }

    @Test
    public void isPassordValid_validPassword_shouldReturnTrue() {
        Assert.assertTrue("Debe retornar true ya que la contraseña es valida",
                RN002.isPasswordValid("madremiawilly"));
    }

    @Test
    public void isPassordValid_invalidPassword_shouldReturnFalse() {
        Assert.assertFalse("Debe retornar false ya que la contraseña es invalida",
                RN002.isPasswordValid(""));
    }

    @Test
    public void isSecondPassordValid_validPassword_shouldReturnTrue() {
        Assert.assertTrue("Debe retornar true ya que las dos contraseñas son validas",
                RN002.isSecondPasswordValid("madremiawilly", "madremiawilly"));
    }

    @Test
    public void isSecondPassordValid_invalidPassword_shouldReturnFalse() {
        Assert.assertFalse("Debe retornar true ya que las dos contraseñas son validas",
                RN002.isSecondPasswordValid("madremiawilly", null));
    }

    @Test
    public void isNameValid_validName_shouldReturnTrue() {
        Assert.assertTrue("Debe retornar true ya que la contraseña es valida",
                RN002.isNameValid("Carlos Tonatihu"));
    }

    @Test
    public void isNameValid_invalidName_shouldReturnFalse() {
        Assert.assertFalse("Debe retornar true ya que la contraseña es valida",
                RN002.isNameValid(" "));
    }

    @Test
    public void isLastNameValid_validLastName_shouldReturnTrue() {
        Assert.assertTrue("Debe retornar true ya que la contraseña es valida",
                RN002.isLastnameValid("Barrera"));
    }

    @Test
    public void isLastNameValid_invalidLastName_shouldReturnFalse() {
        Assert.assertFalse("Debe retornar true ya que la contraseña es valida",
                RN002.isNameValid(null));
    }

    @Test
    public void isProyectoNombreValid_validProyectoNombre_shouldReturnTrue() {
        Assert.assertTrue("Debe retornar true ya que la contraseña es valida",
                RN002.isProyectoNombreValid("Proyecto 1"));
    }

    @Test
    public void isProyectoNombreValid_invalidProyectoNombre_shouldReturnFalse() {
        Assert.assertTrue("Debe retornar true ya que la contraseña es valida",
                RN002.isProyectoNombreValid("1"));
    }
}
