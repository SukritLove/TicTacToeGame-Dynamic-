package com.example.tictactoegame;

import android.provider.BaseColumns;

public class WinnerContract {
    private WinnerContract() {
    }

    public static class WinnerEntry implements BaseColumns {
        public static final String TABLE_NAME = "winners";
        public static final String COLUMN_PLAYER_NAME = "player_name";
        public static final String COLUMN_GRID_SIZE= "grid_size";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_PLAYER_NAME + " TEXT,"+
                        COLUMN_GRID_SIZE +")";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}