package com.adryanev.dicoding.mymoviecatalogue.views.poster.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

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
