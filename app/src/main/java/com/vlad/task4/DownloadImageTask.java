package com.vlad.task4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.InputEvent;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private Context context;


    public DownloadImageTask(Context context) {
        this.context = context;
        Toast.makeText(context, "Please wait, it may take a few seconds.", Toast.LENGTH_SHORT).show();
    }

    protected Bitmap doInBackground(String... urls) {
        try {
            Bitmap bmp = null;
            String longUrl = URLTools.getLongUrl(urls[0]);

            InputStream in = new URL(longUrl).openStream();
            bmp = BitmapFactory.decodeStream(in);
            if (bmp != null)
                return bmp;
        } catch (Exception e) {
            Log.e("Error Message", "Eroare la URLTOOL");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            ((MyApplication) context.getApplicationContext()).setBitmap(result);
            Intent startIntent = new Intent(context, ForeGroundImageService.class);
            startIntent.setAction(ForeGroundImageService.STOPFOREGROUND_ACTION);
            context.startService(startIntent);
        }

    }
}
