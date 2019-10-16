package com.equipo.superttapp.users.repository;

import com.equipo.superttapp.users.data.UsuarioData;

public interface UserRepository {
    UsuarioData login(UsuarioData usuarioData);
    Integer forgotPassword(UsuarioData usuarioData);
    Integer createAccount(UsuarioData usuarioData);
    Integer updateAccount(UsuarioData usuarioData);
}
