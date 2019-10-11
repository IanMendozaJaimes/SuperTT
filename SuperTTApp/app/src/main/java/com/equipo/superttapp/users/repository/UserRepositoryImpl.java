package com.equipo.superttapp.users.repository;


import com.equipo.superttapp.util.ResultCodes;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Integer login(String email, String password) {
        Integer resultado = ResultCodes.SUCCESS;
        return resultado;
    }

    @Override
    public Integer forgotPassword(String email) {
        Integer resultado = ResultCodes.SUCCESS;
        return resultado;
    }

    @Override
    public Integer createAccount(String email, String password, String nombre, String apellidos) {
        Integer resultado = ResultCodes.RN003;
        return resultado;
    }

    @Override
    public Integer updateAccount(String email, String password, String nombre, String apellidos) {
        Integer resultado = ResultCodes.SUCCESS;
        return resultado;
    }

}
