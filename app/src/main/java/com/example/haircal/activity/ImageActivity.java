package com.example.haircal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.haircal.R;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        String path = getIntent().getStringExtra("imgPath");
        ImageView img_big = findViewById(R.id.img_big);
        Uri uri = Uri.parse("file:///"+path);
        img_big.setImageURI(uri);

    }
}
