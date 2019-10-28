package com.equipo.superttapp.users.interactor;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.users.data.UsuarioData;
import com.equipo.superttapp.users.model.UsuarioModel;
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
    public MutableLiveData<BusinessResult<UsuarioModel>> logIn(UsuarioModel usuarioModel) {
        BusinessResult<UsuarioModel> resultado = new BusinessResult<>();
        MutableLiveData<BusinessResult<UsuarioModel>> mutableLiveData = new MutableLiveData<>();
        usuarioModel.setValidPassword(RN002.isPasswordValid(usuarioModel.getPassword()));
        usuarioModel.setValidEmail(RN002.isEmailValid(usuarioModel.getEmail()));
        if (usuarioModel.getValidEmail() && usuarioModel.getValidPassword()) {
            UsuarioData usuarioData = new UsuarioData();
            usuarioData.setEmail(usuarioModel.getEmail());
            usuarioData.setPassword(usuarioModel.getPassword());
            mutableLiveData = repository.login(usuarioData);
        } else {
            resultado.setCode(ResultCodes.RN002);
            resultado.setResult(usuarioModel);
            mutableLiveData.setValue(resultado);
        }

        return mutableLiveData;
    }

    @Override
    public MutableLiveData<BusinessResult<UsuarioModel>> sendEmail(UsuarioModel loginFormModel) {
        BusinessResult<UsuarioModel> result = new BusinessResult<>();
        MutableLiveData<BusinessResult<UsuarioModel>> mutableLiveData = new MutableLiveData<>();
        loginFormModel.setValidEmail(RN002.isEmailValid(loginFormModel.getEmail()));
        if (loginFormModel.getValidEmail()) {
            UsuarioData data = new UsuarioData();
            data.setEmail(loginFormModel.getEmail());
            mutableLiveData = repository.forgotPassword(data);
        }
        else {
            result.setCode(ResultCodes.RN002);
            result.setResult(loginFormModel);
            mutableLiveData.setValue(result);
        }

        return mutableLiveData;
    }

    @Override
    public MutableLiveData<BusinessResult<UsuarioModel>> createAccount(UsuarioModel model) {
        BusinessResult<UsuarioModel> result = new BusinessResult<>();
        MutableLiveData<BusinessResult<UsuarioModel>> mutableLiveData = new MutableLiveData<>();
        model.setValidPassword(RN002.isPasswordValid(model.getPassword()));
        model.setValidEmail(RN002.isEmailValid(model.getEmail()));
        model.setValidSecondPassword(RN002.isSecondPasswordValid(
                model.getPassword(), model.getSecondPassword()));
        model.setValidName(RN002.isNameValid(model.getName()));
        model.setValidLastName(RN002.isLastnameValid(model.getLastname()));
        if (model.getValidPassword() && model.getValidEmail() && model.getValidName()
                && model.getValidSecondPassword() && model.getValidLastName()) {
            UsuarioData data = new UsuarioData();
            data.setEmail(model.getEmail());
            data.setNombre(model.getName());
            data.setApellidos(model.getLastname());
            data.setPassword(model.getPassword());
            data.setCurrentPassword(model.getPassword());
            mutableLiveData = repository.createAccount(data);
        } else {
            result.setCode(ResultCodes.RN002);
            result.setResult(model);
            mutableLiveData.setValue(result);
        }

        return mutableLiveData;
    }

    @Override
    public MutableLiveData<BusinessResult<UsuarioModel>> updateAccount(UsuarioModel model, String token) {
        BusinessResult<UsuarioModel> result = new BusinessResult<>();
        MutableLiveData<BusinessResult<UsuarioModel>> mutableLiveData = new MutableLiveData<>();
        if (model.getCurrentPassword() != null && model.getCurrentPassword().length() > 0) {
            model.setValidPassword(RN002.isPasswordValid(model.getPassword()));
            model.setValidSecondPassword(RN002.isSecondPasswordValid(model.getPassword(),
                    model.getSecondPassword()));
            model.setValidCurrentPassword(RN002.isPasswordValid(model.getCurrentPassword()));
        } else  {
            if ((model.getPassword() != null && model.getPassword().length() > 0)
                    || (model.getSecondPassword() != null && model.getSecondPassword().length() > 0)) {
                model.setValidCurrentPassword(RN002.isPasswordValid(model.getCurrentPassword()));
                model.setValidPassword(RN002.isPasswordValid(model.getPassword()));
                model.setValidSecondPassword(RN002.isSecondPasswordValid(model.getPassword(),
                        model.getSecondPassword()));
            } else {
                model.setValidCurrentPassword(true);
                model.setValidPassword(true);
                model.setValidSecondPassword(true);
            }
        }

        model.setValidName(RN002.isNameValid(model.getName()));
        model.setValidLastName(RN002.isLastnameValid(model.getLastname()));
        model.setValidEmail(true);

        if (model.getValidPassword() && model.getValidName() && model.getValidCurrentPassword()
                && model.getValidSecondPassword() && model.getValidLastName()) {
            UsuarioData data = new UsuarioData();
            data.setId(model.getId());
            data.setNombre(model.getName());
            data.setApellidos(model.getLastname());
            data.setPassword(model.getPassword());
            data.setCurrentPassword(model.getCurrentPassword());
            mutableLiveData = repository.updateAccount(data, token);
            if (mutableLiveData.getValue().getCode().equals(ResultCodes.RN002)) {
                mutableLiveData.getValue().getResult().setValidCurrentPassword(false);
            }
        } else {
            result.setCode(ResultCodes.RN002);
            result.setResult(model);
            mutableLiveData.setValue(result);
        }

        return mutableLiveData;
    }
}
