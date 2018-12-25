package com.adryanev.dicoding.mymoviecatalogue.ui.poster;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class PosterActivity extends AppCompatActivity {
    PhotoView imv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        imv = findViewById(R.id.backdrop_poster);
        Intent i = getIntent();
        String path = i.getStringExtra("path");
        Picasso.get().load(Config.IMAGE_ORIGINAL+path).into(imv);

    }
}
