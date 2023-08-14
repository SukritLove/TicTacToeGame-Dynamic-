package com.example.tictactoegame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HistoryPage extends AppCompatActivity {
    private TextView winOrTie, timePlay, gameType;
    private Button goBack, deleteHistoryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);

        showHistory();
        DeleteButton();
        GoBackButton();
    }

    private void showHistory() {
        winOrTie = findViewById(R.id.winOrTie);
        gameType = findViewById(R.id.gameType);
        winOrTie.setText(makeNewText(getWinnersFromDatabase(1), 1));
        gameType.setText(makeNewText(getWinnersFromDatabase(2), 2));


    }

    private void DeleteButton() {
        deleteHistoryBtn = findViewById(R.id.deleteHistorytBtn);
        deleteHistoryBtn.setOnClickListener(view -> deleteAlert());
    }

    private void deleteAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Clean Slate?");
        alert.setMessage("Wanna wipe the record clean? Say goodbye to past plays?");
        alert.setPositiveButton("Wipe it out!", (dialogInterface, i) -> {
            DatabaseHelper dp = new DatabaseHelper(this);
            dp.deleteAllWinners();
            this.recreate();
        });
        alert.setNegativeButton("Nah, keep it.", (dialogInterface, i) -> {
        });
        alert.create().show();
    }

    private void GoBackButton() {
        goBack = findViewById(R.id.goBackBtn);
        goBack.setOnClickListener(view -> super.onBackPressed());
    }


    public String makeNewText(String[] historyData, int fun) {
        String doneText = "";
        if (fun == 1) {

            for (String _historyData : historyData) {
                doneText += _historyData + "\n";
            }
        } else {
            for (String _historyData : historyData) {
                doneText += _historyData + " x " + _historyData + "\n";
            }
        }


        return doneText;
}

    private String[] getWinnersFromDatabase(int fun) {
        List<String> winners = new ArrayList<>();
        List<String> gridSize = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                WinnerContract.WinnerEntry.COLUMN_PLAYER_NAME,
                WinnerContract.WinnerEntry.COLUMN_GRID_SIZE
        };

        Cursor cursor = db.query(
                WinnerContract.WinnerEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String getWinnerName = cursor.getString(cursor.getColumnIndexOrThrow(WinnerContract.WinnerEntry.COLUMN_PLAYER_NAME));
            String getGridSize = cursor.getString(cursor.getColumnIndexOrThrow(WinnerContract.WinnerEntry.COLUMN_GRID_SIZE));
            winners.add(getWinnerName);
            gridSize.add(getGridSize);
        }

        cursor.close();
        db.close();

        if (fun == 1) {
            String[] winnersArray = winners.toArray(new String[winners.size()]);
            return winnersArray;
        } else {
            String[] gridSizeArray = gridSize.toArray(new String[gridSize.size()]);
            return gridSizeArray;
        }
    }
}


