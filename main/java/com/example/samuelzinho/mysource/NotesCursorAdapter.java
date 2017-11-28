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
 */

public class NotesCursorAdapter extends CursorAdapter {

    public NotesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,
                viewGroup, false);
    }

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
