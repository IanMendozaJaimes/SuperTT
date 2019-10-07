package com.equipo.superttapp.users.repository;

public interface UserRepository {
    Integer login(String email, String password);
    Integer forgotPassword(String email);
    Integer createAccount(String email, String password, String nombre, String apellidos);
}
