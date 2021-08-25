package com.wellstech.tpictest.db;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class ImageRequest extends AsyncTaskLoader {

    String imgUrl;

    public ImageRequest(@NonNull Context context, String imgUrl) {
        super(context);
        this.imgUrl = imgUrl;
    }

    @Nullable
    @Override
    public Bitmap loadInBackground() {
        Bitmap bitmap = null;

        try {
            URL url = new URL(imgUrl);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }
        return bitmap;
    }
}
