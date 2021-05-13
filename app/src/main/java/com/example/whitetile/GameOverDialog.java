package com.example.whitetile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class GameOverDialog extends AppCompatDialogFragment {
    private int score;

    public GameOverDialog(int score) {
        this.score = score;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.game_over_dialog_layout, null);
        builder.setView(view);

        TextView textView = view.findViewById(R.id.score);
        String text = textView.getText().toString().replace("%", String.valueOf(score));
        textView.setText(text);

        Button mainMenu = view.findViewById(R.id.main_menu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        setCancelable(false);

        return builder.create();
    }
}
