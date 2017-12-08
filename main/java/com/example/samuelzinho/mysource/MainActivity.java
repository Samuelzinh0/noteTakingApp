package com.example.samuelzinho.mysource;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.samuelzinho.mysource.EditorActivity;

/**
 * This will be our MainActivity class and it will be the driver to our program.
 * It will create and call any methods, contructors, classes needed to execute
 * our application for the Android mobile operating system.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //These will be our varibales for our MainActivity class they will be a cursor adapter object
    //and a editor request code to use in our application.
    private static final int EDITOR_REQUEST_CODE = 999;
    private CursorAdapter cursorAdapter;

    /**
     * This will be our onCreate function for our main activity and it will be called when
     * the application is first set to run and it will show the user a listView of the notes
     * and other nifty things to help our application work.
     * @param savedInstanceState we will be using this for some conditions in our program
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cursorAdapter = new NotesCursorAdapter(this, null, 0);

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                Uri uri = Uri.parse(DataBaseProvider.CONTENT_URI + "/" + l);
                intent.putExtra(DataBaseProvider.CONTENT_ITEM_TYPE, uri);
                startActivityForResult(intent, EDITOR_REQUEST_CODE);
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * This will be our insert note function and it will just implement and add a new note
     * to the main menu activity screen. It will then allow the user to add data to the
     * database used for this application.
     * @param noteText a string of the text of the note
     */
    private void insertNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(DataBaseOpener.NOTE_TEXT, noteText);
        Uri noteUri = getContentResolver().insert(DataBaseProvider.CONTENT_URI,
                values);

        Log.d("MainActivity", "Inserted note " + noteUri.getLastPathSegment());
    }

    /**
     * This will be our menu creating function that will create the menu on the top right section
     * of our application.
     * @param menu passes in a menu object
     * @return either true or false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This will be our function to work on the conditions if the user was to push on any of the
     * items on our options menu.
     * @param item passes in a MenuItem object
     * @return the certain condition that the user wants
     */
    public boolean onOptionsItemSelected(MenuItem item){
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

    /**
     * This function will be showing up on th emenu on the top right of the program and it will
     * give the opportunity for our users to delete all of the notes on the application if they
     * ever need a quick way of doing it.
     */
    private void deleteAllNotes() {
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        if (button == DialogInterface.BUTTON_POSITIVE) {
                            //Insert Data management code here
                            getContentResolver().delete(
                                    DataBaseProvider.CONTENT_URI, null, null
                            );
                            restartLoader();

                            Toast.makeText(MainActivity.this,
                                    getString(R.string.all_deleted),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(android.R.string.yes), dialogClickListener)
                .setNegativeButton(getString(android.R.string.no), dialogClickListener)
                .show();
    }

    /**
     * This will be for our testing but it also gives the user an opportunity to add notes to the
     * application through different ways. The menu on the top right is one of those different ways.
     */
    private void insertSampleData() {
        insertNote("Simple Note.");
        insertNote("Multi-line\nnote");

        restartLoader();
    }

    //This function will restart the loader for the program.
    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    /**
     * This function will start and create the Loader for our Cursor and it will be a
     * list of cursors to use with our application.
     * @param i passes an initialising integer
     * @param bundle passes in a bundle object
     * @return it will return a new CursorLoader object.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, DataBaseProvider.CONTENT_URI,
                null, null, null,null);
    }

    /**
     * This function will call the cursorAdapter object and will swpa the cursor when needed to.
     * @param loader passes in the loader list
     * @param cursor passes int he cursor object
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    //This function will just reset the loader for our application.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    /**
     * This function will open the editor activity as it will instantiate it and on doing
     * so it will start the editor activity and it will run it and display it on the screen.
     * @param view passes in the view object
     */
    public void openEditorNewNote(View view) {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivityForResult(intent, EDITOR_REQUEST_CODE);

    }

    /**
     * This function will be verifying or checking the activity result and therefore it
     * will call restartLoader function whenever it needs to if the condition is met.
     * @param requestCode passes in the requestCode
     * @param resultCode passes in the resultCode
     * @param data passes in the data intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDITOR_REQUEST_CODE && resultCode == RESULT_OK) {
            restartLoader();
        }
    }
}