package com.tech.mymovieshow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

public class ImageViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        //set full screen for the activity

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();

        ZoomageView zoomageView = findViewById(R.id.zoomageView);

        if(intent != null && intent.getExtras() != null){

            String url = intent.getExtras().getString("image_url");
            if(url != null){
                Picasso.get()
                        .load(url)
                        .into(zoomageView);
            }
        }
    }
}