package com.example.samuelzinho.mysource;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Implementing the new DataBaseOpener class in our main
        DataBaseOpener helper = new DataBaseOpener(this);
        SQLiteDatabase database = helper.getWritableDatabase();

        // Test to make sure the insert method works correctly NB
        // Go to android monitor and search MainActivity to see if
        // the insert worked. NB
        ContentValues values = new ContentValues();
        values.put(DataBaseOpener.NOTE_TEXT, "New Note.");
        Uri noteUri = getContentResolver().insert(DataBaseProvider.CONTENT_URI, values);

        Log.d("MainActivity", "Inserted Note " + noteUri.getLastPathSegment());
    }
}
