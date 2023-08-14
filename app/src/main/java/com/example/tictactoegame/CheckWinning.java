package com.example.tictactoegame;

import android.widget.Button;

import androidx.annotation.NonNull;
public class CheckWinning {
    private int gridSize;
    private Button[][] buttons;
    public CheckWinning(){


    }
    public CheckWinning(Button[][] buttons, int gridSize){
        this.gridSize = gridSize;
        this.buttons = buttons;

    }
    public boolean RowWin() {
        for (int i = 0; i < gridSize; i++) {
            if (!buttons[i][0].getText().toString().isEmpty()) {
                boolean same_Symbol = true;
                for (int j = 1; j < gridSize; j++) {
                    if (!buttons[i][j].getText().equals(buttons[i][0].getText())) {
                        same_Symbol = false;
                        break;
                    }
                }
                if (same_Symbol) {
                    return true;
                }
            }
        }
        return false;

    }
    public boolean ColumnWin() {
        for (int j = 0; j < gridSize; j++) {
            if (!buttons[0][j].getText().toString().isEmpty()) {
                boolean same_Symbol = true;
                for (int i = 1; i < gridSize; i++) {
                    if (!buttons[i][j].getText().equals(buttons[0][j].getText())) {
                        same_Symbol = false;
                        break;
                    }
                }
                if (same_Symbol) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean LeftCrossWin() {

        for (int i = 0; i < gridSize; i++) {
            if (buttons[i][i].getText().toString().isEmpty()) {
                return false; // Anti-diagonal has an empty cell, no win
            }
        }

        String symbol = buttons[0][0].getText().toString();
        for (int i = 1; i < gridSize; i++) {
            if (!buttons[i][i].getText().equals(symbol)) {
                return false; // Different symbols, no win
            }
        }

        return true; // All cells in anti-diagonal have the same symbol

    }
    public boolean RightCrossWin() {
        for (int i = 0; i < gridSize; i++) {
            if (buttons[i][gridSize - i - 1].getText().toString().isEmpty()) {
                return false; // Anti-diagonal has an empty cell, no win
            }
        }

        String symbol = buttons[0][gridSize - 1].getText().toString();
        for (int i = 1; i < gridSize; i++) {
            if (!buttons[i][gridSize - i - 1].getText().equals(symbol)) {
                return false; // Different symbols, no win
            }
        }

        return true; // All cells in anti-diagonal have the same symbol
    }
    public boolean checkForTie() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (buttons[i][j].getText().toString().isEmpty()) {
                    return false; // There are still empty spaces
                }
            }
        }
        return true; // All spaces are filled
    }

    boolean checkForWin() {
        return RowWin() || ColumnWin() || LeftCrossWin() || RightCrossWin();
    }
}
