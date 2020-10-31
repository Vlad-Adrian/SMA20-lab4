package com.vlad.task4;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageactivity_layout);

        MyApplication myApplication = (MyApplication) getApplicationContext();
        if (myApplication.getBitmap() == null) {
            Toast.makeText(this, "Error trasnmitting URL.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(resize(myApplication.getBitmap(),700));
        }
    }

    private Bitmap resize(Bitmap bmp, float size){
        float ratio = Math.min(
                (float) size / bmp.getWidth(),
                (float) size / bmp.getHeight());
        int width = Math.round((float) ratio * bmp.getWidth());
        int height = Math.round((float) ratio * bmp.getHeight());

        Bitmap newBmp = Bitmap.createScaledBitmap(bmp, width,
                height, true);
        return newBmp;
    }
}
