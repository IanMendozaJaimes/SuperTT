package com.equipo.superttapp.users.interactor;

import android.util.Patterns;

import com.equipo.superttapp.users.model.LoginForm;
import com.equipo.superttapp.users.repository.LoginRepository;
import com.equipo.superttapp.users.repository.LoginRepositoryImpl;
import com.equipo.superttapp.util.ResultCodes;

public class LoginInteractorImpl implements LoginInteractor {
    private LoginRepository repository;

    public LoginInteractorImpl() {
        repository = new LoginRepositoryImpl();
    }
    @Override
    public LoginForm logIn(LoginForm loginForm) {
        Integer resultado = ResultCodes.ERROR;
        loginForm.setValidPassword(isPasswordValid(loginForm.getPassword()));
        loginForm.setValidEmail(isEmailValid(loginForm.getEmail()));
        if (loginForm.isValidEmail() && loginForm.isValidPassword())
            resultado = repository.login(loginForm.getEmail(), loginForm.getPassword());
        loginForm.setResultCode(resultado);
        return loginForm;
    }

    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            //return !email.trim().isEmpty();
            return false;
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
