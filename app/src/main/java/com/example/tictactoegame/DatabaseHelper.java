package com.example.tictactoegame;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TicTacToe.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WinnerContract.WinnerEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(WinnerContract.WinnerEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void deleteAllWinners() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WinnerContract.WinnerEntry.TABLE_NAME, null, null);
        db.close();
    }
}
