package com.equipo.superttapp.projects.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.repository.TraduccionRepository;
import com.equipo.superttapp.projects.repository.TraduccionRepositoryImpl;
import com.equipo.superttapp.util.Constants;
import com.equipo.superttapp.util.PreferencesManager;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NewTraduccionActivity extends AppCompatActivity {
    private static final String TAG = NewTraduccionActivity.class.getName();
    private String photoPath;
    private Integer idProyecto;
    TraduccionRepository repository = new TraduccionRepositoryImpl();

    @BindView(R.id.imvPreview)
    ImageView imvPreview;
    @BindView(R.id.btnUpload)
    Button btnUpload;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_traduccion);

        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idProyecto = bundle.getInt(Constants.PROYECTO_ID);
            photoPath = bundle.getString(Constants.TRADUCCION_PATH);
            Picasso.get().load("file:"+photoPath).into(imvPreview);
        }
        PreferencesManager preferencesManager = new PreferencesManager(this,
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferencesManager.keyExists(PreferencesManager.KEY_USER_IS_LOGGED)
                && preferencesManager.getBooleanValue(PreferencesManager.KEY_USER_IS_LOGGED)) {
            token = preferencesManager.getStringValue(PreferencesManager.KEY_USER_TOKEN);
        }
        setTitle(R.string.title_activity_new_traduccion);
    }

    @OnClick(R.id.btnUpload)
    public void onViewClicked(View view) {
        imvPreview.setDrawingCacheEnabled(true);
        imvPreview.buildDrawingCache();
        Bitmap bitmap = imvPreview.getDrawingCache();
        File file = new File(photoPath);

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

        RequestBody proyecto = RequestBody.create(String.valueOf(idProyecto),
                MediaType.parse("text/plain"));
        repository.uploadTraduccion(proyecto, image, token);
    }
}
