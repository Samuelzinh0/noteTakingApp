package com.example.samuelzinho.mysource;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Samuelzinho on 25/11/2017.
 *
 * Even though there is already a cursor adapter class we decided to make an extra class
 * to be able to tailor it to our project. This class extends the CursorsAdapter class as
 * its identifier.
 */
public class NotesCursorAdapter extends CursorAdapter {

    //This will be our constructor for this class.
    public NotesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * This will be the niewView function and it will bne able to reconcile the way that we
     * want the notes to show and display in our UI using a LayoutInflater.
     * @param context passes in the context
     * @param cursor passes in the cursor object
     * @param viewGroup passes in the viewGroup object
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,
                viewGroup, false);
    }

    /**
     * This function will help the view of the UI to be bound to the UI and therefore it
     * will use the string for the note to be added to the notes.
     * @param view passes the view object
     * @param context passes the context object
     * @param cursor passes in the cursor object
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String noteText = cursor.getString(
                cursor.getColumnIndex(DataBaseOpener.NOTE_TEXT));

        int position = noteText.indexOf(10);

        if(position != -1){
            noteText = noteText.substring(0, position) + " ...";
        }

        TextView tv = view.findViewById(android.R.id.text1);
        tv.setText(noteText);
    }
}
