package com.example.whitetile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    ImageView imageView1, imageView2, imageView3;
    LinearLayout ll;
    TextView scoreView;
    ObjectAnimator animator1, animator2, animator3;
    Drawable black, white, red;
    int score, duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        score = 0;

        scoreView = findViewById(R.id.score);

        duration = 10000;

        imageView1 = findViewById(R.id.image1_ll1);
        imageView2 = findViewById(R.id.image2_ll1);
        imageView3 = findViewById(R.id.image3_ll1);
        imageView1.bringToFront();
        imageView2.bringToFront();
        imageView3.bringToFront();

        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(PlayActivity.this, "Game over!", Toast.LENGTH_SHORT).show();
            }
        };

        ll = findViewById(R.id.linear);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlayActivity.this, "Game over!", Toast.LENGTH_SHORT).show();
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        int height = display.getHeight();
        int h = imageView1.getHeight();

        animator1 = ObjectAnimator.ofFloat(imageView1, "translationY",
                -h, height);
        animator1.setDuration(duration);

        animator2 = ObjectAnimator.ofFloat(imageView2, "translationY",
                -h, height);
        animator2.setDuration(duration);

        animator3 = ObjectAnimator.ofFloat(imageView3, "translationY",
                -h, height);
        animator3.setDuration(duration);

        animator1.addListener(animatorListenerAdapter);
        animator2.addListener(animatorListenerAdapter);
        animator3.addListener(animatorListenerAdapter);

        black = AppCompatResources.getDrawable(PlayActivity.this, R.drawable.tile);
        white = AppCompatResources.getDrawable(PlayActivity.this, R.drawable.white);
        red = AppCompatResources.getDrawable(PlayActivity.this, R.drawable.red);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView1.getDrawable() == black) {
                    success(animator1, imageView1);
                } else {
                    imageView1.setImageDrawable(red);
                    Toast.makeText(PlayActivity.this, "Game over!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView2.getDrawable() == black) {
                    success(animator2, imageView2);
                } else {
                    animator1.end();
                    animator2.end();
                    animator3.end();
                    imageView2.setImageDrawable(red);
                    Toast.makeText(PlayActivity.this, "Game over!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView3.getDrawable() == black) {
                    success(animator3, imageView3);
                } else {
                    animator1.end();
                    animator2.end();
                    animator3.end();
                    imageView3.setImageDrawable(red);
                    Toast.makeText(PlayActivity.this, "Game over!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        generateTile();
    }

    private void success(ObjectAnimator animator, ImageView imageView) {
        imageView.setImageDrawable(white);
        score++;
        generateTile();
    }

    private void generateTile() {
        duration += 100;
        Random random = new Random();
        int layoutNo = random.nextInt(3) + 1;
        scoreView.setText(String.valueOf(score));
        switch (layoutNo) {
            case 1:
                imageView1.setImageDrawable(black);
                animator1.start();
                break;
            case 2:
                imageView2.setImageDrawable(black);
                animator2.start();
                break;
            case 3:
                imageView3.setImageDrawable(black);
                animator3.start();
                break;
        }
    }
}