package com.equipo.superttapp.projects.viewmodel;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.equipo.superttapp.projects.interactor.TraduccionInteractor;
import com.equipo.superttapp.projects.interactor.TraduccionInteractorImpl;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.util.BusinessResult;

public class NewTraduccionViewModel extends ViewModel {
    private MutableLiveData<BusinessResult<TraduccionModel>> data = new MutableLiveData<>();
    private TraduccionInteractor interactor = new TraduccionInteractorImpl();

    public MutableLiveData<BusinessResult<TraduccionModel>> uploadImage(TraduccionModel traduccionModel, String token, Bitmap bitmap) {
        data = interactor.uplodadImage(traduccionModel, token, bitmap);
        return data;
    }
}
