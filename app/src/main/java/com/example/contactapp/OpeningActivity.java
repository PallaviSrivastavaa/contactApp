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
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());/*It creates a TranslateAnimation object
        that animates the textView by translating it from -100 pixels on the Y-axis to 0 pixels over a duration of 3000 milliseconds (3 seconds).
         The animation uses an AccelerateDecelerateInterpolator to give it a smooth acceleration and deceleration effect*/

        textView.startAnimation(translateAnimation);



        // Use a Handler to delay the transition to the main layout
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity
                Intent intent = new Intent(OpeningActivity.this, MainActivity.class);
                startActivity(intent);//intent is a messaging object which is used to request any component from another activity

            }
        }, SPLASH_DURATION);/*It uses a Handler to post a delayed task that will run after a specified duration (SPLASH_DURATION).
         In this case, the delay is set to 5000 milliseconds (5 seconds).*/
    }
    }
