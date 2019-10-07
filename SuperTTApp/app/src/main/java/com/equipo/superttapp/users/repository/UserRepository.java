package com.equipo.superttapp.users.repository;

public interface UserRepository {
    Integer login(String email, String password);
    Integer forgotPassword(String email);
}
