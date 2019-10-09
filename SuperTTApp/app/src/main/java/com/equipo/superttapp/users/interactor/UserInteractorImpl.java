package com.equipo.superttapp.users.interactor;

import android.util.Log;

import com.equipo.superttapp.users.model.LoginFormModel;
import com.equipo.superttapp.users.model.SignInFormModel;
import com.equipo.superttapp.users.repository.UserRepository;
import com.equipo.superttapp.users.repository.UserRepositoryImpl;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.RN002;
import com.equipo.superttapp.util.ResultCodes;

public class UserInteractorImpl implements UserInteractor {
    public static final String TAG = UserInteractorImpl.class.getCanonicalName();
    private UserRepository repository;

    public UserInteractorImpl() {
        repository = new UserRepositoryImpl();
    }

    @Override
    public BusinessResult<LoginFormModel> logIn(LoginFormModel loginFormModel) {
        BusinessResult<LoginFormModel> resultado = new BusinessResult<>();
        loginFormModel.setValidPassword(RN002.isPasswordValid(loginFormModel.getPassword()));
        loginFormModel.setValidEmail(RN002.isEmailValid(loginFormModel.getEmail()));
        Log.d(TAG, "email " + loginFormModel.isValidEmail());
        if (loginFormModel.isValidEmail() && loginFormModel.isValidPassword())
            resultado.setCode(repository.login(loginFormModel.getEmail(),
                    loginFormModel.getPassword()));
        resultado.setResult(loginFormModel);
        return resultado;
    }

    @Override
    public BusinessResult<LoginFormModel> sendEmail(LoginFormModel loginFormModel) {
        BusinessResult<LoginFormModel> resultado = new BusinessResult<>();
        loginFormModel.setValidEmail(RN002.isEmailValid(loginFormModel.getEmail()));
        if (loginFormModel.isValidEmail())
            resultado.setCode(repository.forgotPassword(loginFormModel.getEmail()));
        resultado.setResult(loginFormModel);
        return resultado;
    }

    @Override
    public BusinessResult<SignInFormModel> createAccount(SignInFormModel model) {
        BusinessResult<SignInFormModel> result = new BusinessResult<>();
        model.setValidPassword(RN002.isPasswordValid(model.getPassword()));
        model.setValidEmail(RN002.isEmailValid(model.getEmail()));
        model.setValidSecondPassword(RN002.isSecondPasswordValid(
                model.getPassword(), model.getSecondPassword()));
        model.setValidName(RN002.isNameValid(model.getName()));
        model.setValidLastName(RN002.isLastnameValid(model.getLastname()));
        if (model.isValidPassword() && model.isValidEmail() && model.isValidName()
                && model.isValidSecondPassword() && model.isValidLastName()) {
            result.setCode(repository.createAccount(model.getEmail(), model.getPassword(),
                    model.getName(), model.getLastname()));
        }
        result.setResult(model);
        return result;
    }

    @Override
    public  BusinessResult<SignInFormModel>  updateAccount(SignInFormModel model) {
        BusinessResult<SignInFormModel> result = new BusinessResult<>();
        model.setValidPassword(RN002.isPasswordValid(model.getPassword()));
        model.setValidEmail(RN002.isEmailValid(model.getEmail()));
        model.setValidSecondPassword(RN002.isSecondPasswordValid(
                model.getPassword(), model.getSecondPassword()));
        model.setValidName(RN002.isNameValid(model.getName()));
        model.setValidLastName(RN002.isLastnameValid(model.getLastname()));
        if (model.isValidPassword() && model.isValidEmail() && model.isValidName()
                && model.isValidSecondPassword() && model.isValidLastName()) {
            result.setCode(repository.updateAccount(model.getEmail(), model.getPassword(),
                    model.getName(), model.getLastname()));
        }
        result.setResult(model);
        return result;
    }
}
