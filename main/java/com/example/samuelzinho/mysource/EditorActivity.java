package com.example.samuelzinho.mysource;

/**
 * Created by Samuelzinho on 28/11/2017.
 */
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Editor Activity will control all aspects to edit, delete and save notes
 * previous or new.
 */
public class EditorActivity extends AppCompatActivity {

    private String action;
    private EditText editor;
    private String noteFilter;
    private String oldText;

    /**
     * Sets up edit activity when New note button is pushed from main activity
     * @param savedInstanceState Saved instance from previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editor = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();

        Uri uri = intent.getParcelableExtra(DataBaseProvider.CONTENT_ITEM_TYPE);

        if (uri == null){
            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.new_note));

        }
        else{
            action = Intent.ACTION_EDIT;
            noteFilter = DataBaseOpener.NOTE_ID + "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri,
                    DataBaseOpener.ALL_COLUMNS, noteFilter, null, null);
            cursor.moveToFirst();
            oldText = cursor.getString(cursor.getColumnIndex(DataBaseOpener.NOTE_TEXT));
            editor.setText(oldText);
            editor.requestFocus();
        }
    }

    /**
     * Enables the option menu in top right corner of edit activity
     * @param menu Data that hold user actions to create sample data
     *             and deletion of all notes
     * @return Always available in edit activity
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (action.equals(Intent.ACTION_EDIT)){
            getMenuInflater().inflate(R.menu.menu_editor, menu);
        }
        return true;
    }

    /**
     * List the option user has from option menu - create sample data
     * and delete all notes
     * @param item Lets user choose 1 of 2 option items for edit activity
     * @return Always available to edit activity
     */
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (item.getItemId()){
            case android.R.id.home:
                finishEditing();
                break;
            case R.id.action_delete:
                deleteNote();
                break;
        }
        return true;
    }

    /**
     * Allows user to delete a selected note. Appears in the Editor Activity
     * after deletion notifies user of deleted note.
     */
    private void deleteNote() {
        getContentResolver().delete(DataBaseProvider.CONTENT_URI,
                noteFilter, null);
        Toast.makeText(this, R.string.note_deleted, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    /**
     * User pushes back button in Editor Activity to save the note in a saved
     * persistance state, in this case a SQLite Database
     */
    private void finishEditing() {
        String newText = editor.getText().toString().trim();

        switch(action){
            case Intent.ACTION_INSERT:
                if (newText.length() ==  0){
                    setResult(RESULT_CANCELED);
                }
                else{
                    insertNote(newText);
                }
                break;
            case Intent.ACTION_EDIT:
                if(newText.length() == 0){
                    deleteNote();
                }
                else if (oldText.equals(newText)) {
                    setResult(RESULT_CANCELED);
                }
                else {
                    updateNote(newText);
                }
        }
        finish();
    }

    /**
     * If user updates note on Editor activity allows note to be edited
     * @param noteText previous note text that existed in SQLite database
     */
    private void updateNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(DataBaseOpener.NOTE_TEXT, noteText);
        getContentResolver().update(DataBaseProvider.CONTENT_URI, values, noteFilter, null);
        Toast.makeText(this, R.string.note_updated, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    /**
     * Works with updateNote, but insertNote takes action to insert (save persistance)
     * new text into previous saved SQLite database
     * @param noteText previous note text that existed in SQLite database
     */
    private void insertNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(DataBaseOpener.NOTE_TEXT, noteText);
        getContentResolver().insert(DataBaseProvider.CONTENT_URI, values);
        setResult(RESULT_OK);
    }

    /**
     * When the backbutton is pushed edit activity is done with editing
     */
    @Override
    public void onBackPressed() {
        finishEditing();
    }
}
