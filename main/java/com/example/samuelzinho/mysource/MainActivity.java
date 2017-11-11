package com.example.samuelzinho.mysource;

import android.content.ContentValues;
//import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.net.Uri;
//import android.provider.ContactsContract;
import android.app.LoaderManager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;














//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.net.Uri;

//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.CursorLoader;
//import android.support.v4.content.Loader;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.CursorAdapter;
//import android.widget.ListView;
//import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

    // Instantiate CursorAdapter object outside of scope of onCreate
    // or new Activity
    private CursorAdapter cursorAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertNote("New Note");
        insertNote("New Note");
        insertNote("New Note");
        insertNote("New Note");
        insertNote("New Note");

        //Cursor cursor = getContentResolver().query(DataBaseProvider.CONTENT_URI,DataBaseOpener.ALL_COLUMNS,null,null,null,null);
        String[] from = { DataBaseOpener.NOTE_TEXT };
        int[] to = {android.R.id.text1};
        cursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,null,from,to,0);
        //cursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,from,to,0);

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);

        // this doesnt work?
        getLoaderManager().initLoader(0, null, this);

    }

    /**
     * This will be my method to insert a new note to our application. We will be
     * using a values object. We will call the put method in order to insert a new note.
     * We will also be using the Uri class here to identify a resource.
     * @param noteText
     */
    private void insertNote(String noteText){
        ContentValues values = new ContentValues();
        values.put(DataBaseOpener.NOTE_TEXT, noteText);
        //values.put(DataBaseOpener.NOTE_TEXT,noteText);
        Uri noteUri = getContentResolver().insert(DataBaseProvider.CONTENT_URI,values);

        Log.d("MainActivity", "Inserted Note " + noteUri.getLastPathSegment());
    }

    /**
     * onCreateLoader will update with id and args parameter
     * @param int IT, Bundle args
     * @return Loader<Cursor>
     */
    @Override
    public android.content.Loader<Object> onCreateLoader(int id, Bundle args) {

        // return new Cursor list using provider for notes database
        return new android.content.Loader<>(this);

        //return new android.content.Loader<>(this, DataBaseProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Object> loader, Object data) {
        cursorAdapter.swapCursor((Cursor) data);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Object> loader) {
        cursorAdapter.swapCursor(null);
    }

    /**
     * onLoadFinished will handle (update with data) when finished
     * @param Loader<Cursor> loader, Cursor data
     * @return void
     */
   // @Override
  //  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

  //      // cursor for when finished
 //       cursorAdapter.swapCursor(data);
 //   }

    /**
     * onLoadReset will handle the reset (null)
     * @param Loader<Cursor> loader
     * @return void
     */
    //@Override
   // public void onLoaderReset(Loader<Cursor> loader) {

     //   // cursor for reset (uses null)
    //    cursorAdapter.swapCursor(null);
 //   }
}
