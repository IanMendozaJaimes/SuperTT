package com.equipo.superttapp.projects.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.repository.TraduccionRepository;
import com.equipo.superttapp.projects.repository.TraduccionRepositoryImpl;
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
    private static final String PHOTO_KEY = "IMAGE_PATH_TEMP";
    private static final String TAG = NewTraduccionActivity.class.getName();
    private String photoPath;
    TraduccionRepository repository = new TraduccionRepositoryImpl();

    @BindView(R.id.imvPreview)
    ImageView imvPreview;
    @BindView(R.id.btnUpload)
    Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_traduccion);

        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            photoPath = bundle.getString(PHOTO_KEY);
            Picasso.get().load("file:"+photoPath).into(imvPreview);
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
        MultipartBody.Part image = MultipartBody.Part.createFormData("image",
                file.getName(), fileReqBody);

        RequestBody idUsuario = RequestBody.create("1", MediaType.parse("text/plain"));
        RequestBody idProyecto = RequestBody.create("90", MediaType.parse("text/plain"));
        repository.uploadTraduccion(idUsuario, idProyecto, image);
    }
}
