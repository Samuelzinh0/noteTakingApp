package com.example.samuelzinho.mysource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Samuelzinho on 04/11/2017.
 * This will be my DataBaseOpener class and it will extend the SQLiteOpenHelper
 * in order fo rme to start programming a data base for our application.
 */

public class DataBaseOpener extends SQLiteOpenHelper {

    /**
     * Here I am creating just the implementation of the onCreate function and
     * onUpgrade function in order for oue DataBaseOpener class to work. I will
     * also be declaring the right constants for our database.
     * @param sqLiteDatabase
     */

    /**
     * I will now add the constants for our database and this will determine
     * the characteristics of our database.
     */
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    // I updated the names of the constants to follow standard naming conventions for constants. NB
    public static final String TABLE_NOTES = "notes";
    public static final String NOTE_ID = "_id";
    public static final String NOTE_TEXT = "Text";
    public static final String NOTE_CREATED = "Created";


    public static final String[] ALL_COLUMNS = {NOTE_ID, NOTE_TEXT, NOTE_CREATED};

    // Anything in quote marks "" in this method is standard SQL syntax for creating a new table.
    // It cannot be changed to anything else or the database will not work. NB
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOTE_TEXT + " TEXT, " +
                    NOTE_CREATED + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    public DataBaseOpener(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_NOTES);
        onCreate(sqLiteDatabase);
    }
}
