package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class OpeningActivity extends AppCompatActivity {
    private ImageView gif;
    private TextView textView;
    private static final long SPLASH_DURATION = 5000; // Duration in milliseconds (10 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        gif =findViewById(R.id.gif);
        Glide.with(this).load(R.drawable.anime).into(gif);
        textView=findViewById(R.id.textView);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -100, 0);
        translateAnimation.setDuration(3000); // Duration of the translation animation in milliseconds
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        textView.startAnimation(translateAnimation);



        // Use a Handler to delay the transition to the main layout
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity
                Intent intent = new Intent(OpeningActivity.this, MainActivity.class);
                startActivity(intent);

            }
        }, SPLASH_DURATION);
    }
    }
