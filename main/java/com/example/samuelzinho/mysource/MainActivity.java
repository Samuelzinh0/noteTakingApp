package com.example.samuelzinho.mysource;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
//import android.app.LoaderManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Instantiate CursorAdapter object outside of scope of onCreate
    // or new Activity
    private CursorAdapter cursorAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertNote("New Note");

        //Cursor cursor = getContentResolver().query(DataBaseProvider.CONTENT_URI,DataBaseOpener.ALL_COLUMNS,null,null,null,null);
        String[] from = { DataBaseOpener.NOTE_TEXT };
        int[] to = {android.R.id.text1};
        cursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,null,from,to,0);
        //cursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,from,to,0);

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);

        // this doesnt work?
        //getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_create_sample:
                insertSampleData();
                break;
            case R.id.action_delete_all:
                deleteAllNotes();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {
        // Ask for confirmation to delete all notes.
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        if (button == DialogInterface.BUTTON_POSITIVE) {
                            // If the user clicks 'yes', this will run.
                            getContentResolver().delete(DataBaseProvider.CONTENT_URI, null, null);

                            //restartLoader();

                            getContentResolver().delete(DataBaseProvider.CONTENT_URI, null, null);

                            //restartLoader();

                            Toast.makeText(MainActivity.this, getString(R.string.all_deleted), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(android.R.string.yes), dialogClickListener)
                .setNegativeButton(getString(android.R.string.no), dialogClickListener)
                .show();
    }

    private void insertSampleData() {
        insertNote("Sample Note");
        insertNote("Multi-Line\nNote");
        insertNote("Very long note with a lot of text that exceeds the width of the screen");
    }

    /**
     * This will be my method to insert a new note to our application. We will be
     * using a values object. We will call the put method in order to insert a new note.
     * We will also be using the Uri class here to identify a resource.
     * @param noteText
     */
    private void insertNote(String noteText){
        ContentValues values = new ContentValues();
        values.put(DataBaseOpener.NOTE_TEXT,noteText);
        Uri noteUri = getContentResolver().insert(DataBaseProvider.CONTENT_URI,values);

        Log.d("MainActivity", "Inserted Note " + noteUri.getLastPathSegment());
    }

    /**
     * onCreateLoader will update with id and args parameter
     * @param id, Bundle args
     * @return Loader<Cursor>
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // return new Cursor list using provider for notes database
        return new CursorLoader(this, DataBaseProvider.CONTENT_URI, null, null, null, null);
    }

    /**
     * onLoadFinished will handle (update with data) when finished
     * @param loader<Cursor> loader, Cursor data
     * @return void
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        // cursor for when finished
        cursorAdapter.swapCursor(data);
    }

    /**
     * onLoadReset will handle the reset (null)
     * @param loader<Cursor> loader
     * @return void
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        // cursor for reset (uses null)
        cursorAdapter.swapCursor(null);
    }
}
