package com.example.whitetile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button playButton, scoresButton, shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.play_button);
        scoresButton = findViewById(R.id.highscore_button);
        shareButton = findViewById(R.id.share_button);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                startActivity(intent);
                finish();
            }
        });

        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScoresActivity.class);
                startActivity(intent);
                finish();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(PlayActivity.SHARED_PREFERENCES, MODE_PRIVATE);
                String scores = sharedPreferences.getString(PlayActivity.SCORES, "");
                List<String> temp = new ArrayList<>(Arrays.asList(scores.split(",")));
                List<Integer> scoreList = new ArrayList<>();
                for (String s : temp) {
                    scoreList.add(Integer.parseInt(s));
                }
                java.util.Collections.sort(scoreList);

                String string = "Hi! I played WhiteTile and my highscore was " + scoreList.get(scoreList.size() - 1) + "! =)";

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, string);
                intent.putExtra(Intent.EXTRA_TITLE, string);

                boolean facebookAppFound = false;
                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Text", string);
                        assert clipboard != null;
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(MainActivity.this, "Text copied to clipboard!", Toast.LENGTH_SHORT).show();
                        intent.setPackage(info.activityInfo.packageName);
                        facebookAppFound = true;
                        break;
                    }
                }

                if (!facebookAppFound) {
                    Toast.makeText(MainActivity.this, "You don't have facebook installed :(", Toast.LENGTH_SHORT).show();
                } else startActivity(intent);
            }
        });
    }
}