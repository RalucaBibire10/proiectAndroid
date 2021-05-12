package com.example.whitetile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    ImageView imageView11, imageView12, imageView13;
    ImageView imageView21, imageView22, imageView23;
    ImageView imageView31, imageView32, imageView33;
    ImageView imageView41, imageView42, imageView43;
    ImageView imageView51, imageView52, imageView53;
    TextView scoreView;
    Drawable black, white, red;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        score = 0;

        scoreView = findViewById(R.id.score);

        imageView11 = findViewById(R.id.image1_ll1);
        imageView12 = findViewById(R.id.image2_ll1);
        imageView13 = findViewById(R.id.image3_ll1);

        imageView21 = findViewById(R.id.image1_ll2);
        imageView22 = findViewById(R.id.image2_ll2);
        imageView23 = findViewById(R.id.image3_ll2);

        imageView31 = findViewById(R.id.image1_ll3);
        imageView32 = findViewById(R.id.image2_ll3);
        imageView33 = findViewById(R.id.image3_ll3);

        imageView41 = findViewById(R.id.image1_ll4);
        imageView42 = findViewById(R.id.image2_ll4);
        imageView43 = findViewById(R.id.image3_ll4);

        imageView51 = findViewById(R.id.image1_ll5);
        imageView52 = findViewById(R.id.image2_ll5);
        imageView53 = findViewById(R.id.image3_ll5);

        black = AppCompatResources.getDrawable(PlayActivity.this, R.drawable.tile);
        white = AppCompatResources.getDrawable(PlayActivity.this, R.drawable.white);
        red = AppCompatResources.getDrawable(PlayActivity.this, R.drawable.red);

        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageView11.getDrawable() == black) {
                    imageView11.setImageDrawable(white);
                    score++;
                    generateTile();
                } else {
                    imageView11.setImageDrawable(red);
                    Toast.makeText(PlayActivity.this, "Game over!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView12.getDrawable() == black) {
                    imageView12.setImageDrawable(white);
                    score++;
                    generateTile();
                } else {
                    imageView12.setImageDrawable(red);
                    Toast.makeText(PlayActivity.this, "Game over!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView13.getDrawable() == black) {
                    imageView13.setImageDrawable(white);
                    score++;
                    generateTile();
                } else {
                    imageView13.setImageDrawable(red);
                    Toast.makeText(PlayActivity.this, "Game over!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        generateTile();
    }

    private void generateTile() {
        Random random = new Random();
        int layoutNo = random.nextInt(3) + 1;
        scoreView.setText(String.valueOf(score));
        switch (layoutNo) {
            case 1:
                imageView11.setImageDrawable(black);
                break;
            case 2:
                imageView12.setImageDrawable(black);
                break;
            case 3:
                imageView13.setImageDrawable(black);
                break;
        }
    }
}