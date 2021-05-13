package com.example.whitetile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoresActivity extends AppCompatActivity {
    private String scores;
    private List<String> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        scoreList = new ArrayList<>();
        getScores();

        ListView listView = findViewById(R.id.list);

        java.util.Collections.sort(scoreList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scoreList);
        listView.setAdapter(arrayAdapter);

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
        scoreList.addAll(temp);
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