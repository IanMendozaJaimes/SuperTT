package com.equipo.superttapp.users.repository;

public interface LoginRepository {
    Integer login(String correo, String password);
}
