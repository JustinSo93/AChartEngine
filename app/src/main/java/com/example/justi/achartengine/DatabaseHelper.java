package com.example.justi.achartengine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by justi on 11/12/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int database_version = 2;
    protected static final String ID = "KEY_ID";
    protected static final String type = "Type";
    protected static final String expenses = "expenses";
    private static final String createDatabase = "Create table summary ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + type + " TEXT NOT NULL, " + expenses + " INTEGER)";

    public DatabaseHelper(Context c){
        super(c, "Expense Database", null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createDatabase);
        Log.i("DatabaseHelper", "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "expenses");
    }
}
