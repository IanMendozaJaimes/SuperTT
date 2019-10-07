package com.equipo.superttapp.users.repository;


import com.equipo.superttapp.util.ResultCodes;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Integer login(String email, String password) {
        Integer resultado = ResultCodes.ERROR;
        return resultado;
    }

    @Override
    public Integer forgotPassword(String email) {
        Integer resultado = ResultCodes.SUCCESS;
        return resultado;
    }

}
