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
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + createTable);
        onCreate(sqLiteDatabase);
    }

    /**
     * I will now add the constants for our database and this will determine
     * the characteristics of our database.
     */
    private static final String databaseName = "notes.db";
    private static final int databaseVersion = 1;

    public static final String noteTable = "notes";
    public static final String noteID = "ID";
    public static final String noteText = "Text";
    public static final String noteCreated = "Created";

    public static final String[] allColumns = {noteTable, noteID, noteText, noteCreated};

    private static final String createTable = "Create Table" + noteTable + " (" + noteID
            + "Integer Increment for the Primary Key," + noteText + " text, " + noteCreated
            + " text default current timestamp" + ")";

    public DataBaseOpener(Context context){
        super(context, databaseName,null,databaseVersion);
    }
}
