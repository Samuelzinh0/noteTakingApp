package com.example.samuelzinho.mysource;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Implementing the new DataBaseOpener class in our main
        DataBaseOpener helper = new DataBaseOpener(this);
        SQLiteDatabase database = helper.getWritableDatabase();
    }
}
