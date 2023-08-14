package com.example.tictactoegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private GridLayout gridLayout;
    private TextView whoPlay;
    private Button resetBtn, exitBtn;
    private CheckWinning _CheckWinning = new CheckWinning();
    private getPlayer _getPlayer = new getPlayer();
    private boolean isXTurn = true;
    private long gameStartTime;
    private long timerDuration = 0;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent grid = getIntent();
        int gridSize = grid.getIntExtra("gridSize", 3);
        Button[][] buttons = new Button[gridSize][gridSize];

        gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setRowCount(gridSize);
        gridLayout.setColumnCount(gridSize);

        whoPlay = findViewById(R.id.whoPlay);
        showPlayer();

        createDynamicButtons(buttons, gridSize);
        ResetButton(buttons, gridSize);
        ExitButton();
    }

    private void createDynamicButtons(Button[][] buttons, int gridSize) {

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {

                final Button button = new Button(this);
                button.setTag(new ButtonPosition(i, j));

                int uniqueId = i * gridSize + j + 1;
                button.setId(uniqueId);

                button.setTextSize(btnTextSize(gridSize));
                button.setTextColor(getResources().getColor(R.color.black));
                button.setBackgroundResource(R.drawable.buttonsquare);

                //------Set Button to Layout------
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 2f);
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 2f);
                params.setMargins(10, 10, 10, 10);
                //------Set Button to Layout------

                button.setLayoutParams(params);
                button.setOnClickListener(view -> handleButtonClick(button, buttons, gridSize));


                buttons[i][j] = button;
                gridLayout.addView(button);
            }
        }
    }

    private int btnTextSize(int gridSize) {
        switch (gridSize) {
            case 3:
                return 50;
            case 4:
                return 40;
            case 5:
                return 30;
            case 6:
                return 20;
            case 7:
                return 19;
        }
        return 0;
    }

    private void ResetButton(Button[][] buttons, int gridSize) {
        resetBtn = findViewById(R.id.resetBtn);
        resetBtn.setVisibility(View.GONE);
        gameStartTime = System.currentTimeMillis();
        resetBtn.setOnClickListener(view -> {
            resetGame(buttons, gridSize);
            showPlayer();
            resetBtn.setVisibility(View.GONE);
            if (_getPlayer.getLastWinner().equals("O")) {
                isXTurn = !isXTurn;
            }
        });
    }

    private void ExitButton() {
        exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(view -> {
            quitAlert();
        });

    }

    private void quitAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Tic-Tac-Go?");
        alert.setMessage("Exit now? Battle's still on!");
        alert.setPositiveButton("Yep, All good", (dialogInterface, i) -> {
            super.onBackPressed();
        });
        alert.setNegativeButton("Not yet!", (dialogInterface, i) -> {
        });
        alert.create().show();
    }

    public void onBackPressed() {
        quitAlert();
    }

    private void handleButtonClick(@NonNull Button button, Button[][] buttons, int gridSize) {
        _CheckWinning = new CheckWinning(buttons, gridSize);
        if (button.getText().toString().isEmpty() && !_CheckWinning.checkForWin()) {

            if (isXTurn) {
                button.setText("X");
            } else {
                button.setText("O");
            }
            isXTurn = !isXTurn;
            whoPlay.setText(isXTurn ? "Player 1 [X turn]" : "Player 2 [O turn]");


            if (_CheckWinning.checkForWin()) {
                String winner = isXTurn ? "Player O" : "Player X";
                WhoWin(winner + " wins!", gridSize);
            } else if (_CheckWinning.checkForTie()) {
                WhoWin("It's a Tie", gridSize);
            }
        }

    }

    private void WhoWin(String message, int gridSize) {
        resetBtn.setVisibility(View.VISIBLE);
        whoPlay.setText(message);
        _getPlayer.setLastWinner(message.split(" ")[1]);
        insertWinnerIntoDatabase(message, String.valueOf(gridSize));
    }

    private void insertWinnerIntoDatabase(String winnerName, String gridSize) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WinnerContract.WinnerEntry.COLUMN_PLAYER_NAME, winnerName);
        values.put(WinnerContract.WinnerEntry.COLUMN_GRID_SIZE, gridSize);

        long newRowId = db.insert(WinnerContract.WinnerEntry.TABLE_NAME, null, values);
        if (newRowId != -1) {
            // Insert successful
            Log.d("Database", "Winner inserted with ID: " + newRowId);
        } else {
            // Insert failed
            Log.e("Database", "Error inserting winner");
        }
        db.close();
    }

    private void resetGame(Button[][] buttons, int gridSize) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        isXTurn = true;
    }

    private void showPlayer() {
        whoPlay.setText(_getPlayer.getLastWinner().equals("a") ||
                _getPlayer.getLastWinner().equals("X") ?
                "Player 1 [X turn]" : "Player 2 [O turn]");
    }




    //-----Get Last Player Who win-----
    public class getPlayer {
        private String lastWinner;

        public void setLastWinner(String winner) {
            lastWinner = winner;
        }

        public String getLastWinner() {
            return lastWinner != null ? lastWinner : "X";
        }
    }
    //-----Get Last Player Who win-----

    //-----Set position-----
    private class ButtonPosition {
        int row, column;

        ButtonPosition(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
    //-----Set position-----


}