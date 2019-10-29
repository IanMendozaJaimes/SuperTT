package com.equipo.superttapp.projects.interactor;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.projects.repository.TraduccionRepository;
import com.equipo.superttapp.projects.repository.TraduccionRepositoryImpl;
import com.equipo.superttapp.util.BusinessResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class TraduccionInteractorImpl implements TraduccionInteractor{
    private TraduccionRepository repository;

    public TraduccionInteractorImpl() {
        repository = new TraduccionRepositoryImpl();
    }
    @Override
    public MutableLiveData<BusinessResult<TraduccionModel>> findAllTraduccionesByProyecto(Integer idProyecto, String key) {
        return repository.findAllTraduccionesByProyecto(idProyecto, key);
    }

    @Override
    public MutableLiveData<BusinessResult<TraduccionModel>> deleteTraduccion(Integer idTraduccion, String token) {
        MutableLiveData<BusinessResult<TraduccionModel>> data = new MutableLiveData<>();
        data = repository.deleteTraduccion(idTraduccion, token);
        return data;
    }

    @Override
    public MutableLiveData<BusinessResult<TraduccionModel>> uplodadImage(TraduccionModel traduccionModel, String token, Bitmap bitmap) {
        MutableLiveData<BusinessResult<TraduccionModel>> data = new MutableLiveData<>();

        File file = new File(traduccionModel.getUrl());

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody fileReqBody = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(),
                fileReqBody);

        RequestBody proyecto = RequestBody.create(String.valueOf(traduccionModel.getIdProyecto()),
                MediaType.parse("text/plain"));
        RequestBody mediaType = RequestBody.create("jpg", MediaType.parse("text/plain"));

        data = repository.uploadTraduccion(proyecto, image, mediaType, token);
        return data;
    }

}
