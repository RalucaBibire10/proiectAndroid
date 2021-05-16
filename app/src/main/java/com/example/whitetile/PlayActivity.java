package com.example.whitetile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    private LinearLayout ll;
    private TextView scoreView;
    private Drawable black, white, red;
    private MediaPlayer mediaPlayer1, mediaPlayer2, mediaPlayer3, gameOver;
    private List<Tile> tiles;
    private int score, duration, h, height;
    private boolean end;
    private LinearInterpolator linearInterpolator;
    protected static final String SHARED_PREFERENCES = "WhiteTileScores";
    protected static final String SCORES = "SCORES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        score = 0;
        end = false;

        scoreView = findViewById(R.id.score);

        duration = 10000;

        mediaPlayer1 = MediaPlayer.create(this, R.raw.sound1);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.sound2);
        mediaPlayer3 = MediaPlayer.create(this, R.raw.sound3);
        gameOver = MediaPlayer.create(this, R.raw.lost);

        ll = findViewById(R.id.linear);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame();
            }
        });
        tiles = new ArrayList<>();
        linearInterpolator = new LinearInterpolator();

        Display display = getWindowManager().getDefaultDisplay();
        height = display.getHeight();
        h = (int) getResources().getDimension(R.dimen.image_height);

        black = AppCompatResources.getDrawable(PlayActivity.this, R.drawable.tile);
        white = AppCompatResources.getDrawable(PlayActivity.this, R.drawable.white);
        red = AppCompatResources.getDrawable(PlayActivity.this, R.drawable.red);

        startGame();
    }

    private void success(Tile tile) {
        tile.getImageView().setImageDrawable(white);
        tile.getMediaPlayer().start();
        tile.setCanceled(true);
        tile.getAnimator().cancel();
        score++;
        scoreView.setText(String.valueOf(score));
        if (duration > 100) {
            duration -= 100;
        }
        tiles.remove(tile);
        //generateTile();
    }

    private void generateTile() {
        Handler handler = new Handler(Looper.getMainLooper());
        if (!end) {
            Random random = new Random();
            int layoutNo = random.nextInt(3) + 1;
            switch (layoutNo) {
                case 1:
                    RelativeLayout relativeLayout1 = findViewById(R.id.relative1);
                    createIV(relativeLayout1, mediaPlayer1);
                    break;
                case 2:
                    RelativeLayout relativeLayout2 = findViewById(R.id.relative2);
                    createIV(relativeLayout2, mediaPlayer2);
                    break;
                case 3:
                    RelativeLayout relativeLayout3 = findViewById(R.id.relative3);
                    createIV(relativeLayout3, mediaPlayer3);
                    break;
            }
            handler.postDelayed(this::generateTile, duration / 10); //This posts the task AGAIN, with 10 seconds delay}
        } else {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void imageChanged(ObjectAnimator animator) {
        animator.start();
    }

    private void createIV(RelativeLayout relativeLayout, MediaPlayer mediaPlayer) {
        ImageView imageView = new ImageView(relativeLayout.getContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, h);
        imageView.setLayoutParams(lp);
        imageView.setImageDrawable(black);
        imageView.bringToFront();
        relativeLayout.addView(imageView);

        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationY",
                -h, height);

        animator.setDuration(duration);
        animator.setInterpolator(linearInterpolator);

        Tile t = new Tile(mediaPlayer, animator, imageView, relativeLayout);
        tiles.add(t);

        imageChanged(t.getAnimator());

        t.getAnimator().addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!t.getIsCanceled()) {
                    endGame();
                } else t.setCanceled(true);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t.getImageView().getDrawable() == black) {
                    t.setCanceled(true);
                    success(t);
                }else if (t.getImageView().getDrawable()==white){
                    endGame();
                }
            }
        });
    }

    private void startGame() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(this::generateTile, duration / 10);
    }

    private void endGame() {
        gameOver.start();
        for (Tile tile : tiles) {
            tile.setCanceled(true);
            tile.getAnimator().cancel();
            tile.getMediaPlayer().stop();
        }
        end = true;

        saveScore();

        GameOverDialog dialog = new GameOverDialog(score);
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    protected void saveScore() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String scores = sharedPreferences.getString(SCORES, "");
        if (scores.equals("")) {
            scores = String.valueOf(score);
        } else {
            scores = scores + "," + score;
        }
        editor.putString(SCORES, scores);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}