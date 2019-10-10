package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.model.SignInFormModel;
import com.equipo.superttapp.util.BusinessResult;

public interface SignInPresenter {
    void signIn(SignInFormModel model);
    void showMessage(BusinessResult<SignInFormModel> result);
}
