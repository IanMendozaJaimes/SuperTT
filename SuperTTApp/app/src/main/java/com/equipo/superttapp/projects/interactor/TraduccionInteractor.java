package com.equipo.superttapp.projects.interactor;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.util.BusinessResult;

public interface TraduccionInteractor {
    MutableLiveData<BusinessResult<TraduccionModel>> findAllTraduccionesByProyecto(Integer idProyecto, String key);
    MutableLiveData<BusinessResult<TraduccionModel>> deleteTraduccion(Integer idTraduccion, String token);
    MutableLiveData<BusinessResult<TraduccionModel>> uplodadImage(TraduccionModel traduccionModel, String token, Bitmap bitmap);
}
