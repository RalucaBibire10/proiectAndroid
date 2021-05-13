package com.example.whitetile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoresActivity extends AppCompatActivity {
    private String scores;
    private List<Integer> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        scoreList = new ArrayList<>();
        getScores();

        ListView listView = findViewById(R.id.list);

        java.util.Collections.reverse(scoreList);

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, scoreList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "Hi! I played WhiteTile and my score was " + scoreList.get(position) + "! =)";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Share your score!"));
            }
        });

        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPreferences();
                scoreList.clear();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getScores() {
        SharedPreferences sharedPreferences = getSharedPreferences(PlayActivity.SHARED_PREFERENCES, MODE_PRIVATE);
        scores = sharedPreferences.getString(PlayActivity.SCORES, "");
        List<String> temp = Arrays.asList(scores.split(","));
        for (String s : temp) {
            scoreList.add(Integer.parseInt(s));
        }
        java.util.Collections.sort(scoreList);
    }

    private void clearPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PlayActivity.SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PlayActivity.SCORES, "");
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}