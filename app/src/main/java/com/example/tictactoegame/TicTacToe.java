package com.example.tictactoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TicTacToe extends AppCompatActivity {
    private Button plus, minus, startGame, historyBtn, exitBtn;
    private TextView gridSizeSh1, gridSizeSh2;
    int curentNumber = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);


        gridSizeSh1 = findViewById(R.id.gridSize1);
        gridSizeSh2 = findViewById(R.id.gridSize2);
        startGame = findViewById(R.id.startGame);

        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        howManyGrid();
        HistoryButton();
        ExitButton();

    }

    private void howManyGrid() {
        gridSizeSh1.setText(String.valueOf(curentNumber));
        gridSizeSh2.setText(String.valueOf(curentNumber));

        plus.setOnClickListener(view -> {
            if (curentNumber < 7) {
                curentNumber++;
                gridSizeSh1.setText(String.valueOf(curentNumber));
                gridSizeSh2.setText(String.valueOf(curentNumber));
            }
        });

        minus.setOnClickListener(view -> {
            if (curentNumber > 3) {
                curentNumber--;
                gridSizeSh1.setText(String.valueOf(curentNumber));
                gridSizeSh2.setText(String.valueOf(curentNumber));
            }
        });
        startGame.setOnClickListener(view ->
                GameStart());
    }

    public void GameStart() {
        Intent start = new Intent(getApplicationContext(), MainActivity.class);
        start.putExtra("gridSize", curentNumber);
        startActivity(start);

    }

    private void HistoryButton() {
        historyBtn = findViewById(R.id.historyBtn);
        historyBtn.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), HistoryPage.class))
        );
    }

    private void ExitButton() {
        exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(view ->
                super.onBackPressed());
    }


}