package com.example.samuelzinho.mysource;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * DataBaseProvider class will inherit from content provider.
 * The content provider will share data between main activity
 * and SQLiteDatabase.
 * @author Tim Whitlock
 * @version 1.0
 * @since 11/6/2017
 */

public class DataBaseProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.samuelzinho.mysource.DataBaseProvider";
    private static final String BASE_PATH = "notes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String CONTENT_ITEM_TYPE = "Notes";

    // Identifies the requested operation
    private static final int NOTES = 1;
    private static final int NOTES_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY,BASE_PATH,NOTES);
        uriMatcher.addURI(AUTHORITY,BASE_PATH + "/#",NOTES_ID);
    }

    private SQLiteDatabase database;

    /**
     * onCreate will simply initializes the content provider
     * @return boolean (did provider initialize?)
     */
    @Override
    public boolean onCreate() {

        // Create DataBaseOpener object to initialize content provider
        DataBaseOpener helper = new DataBaseOpener(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    /**
     * Query will return cursor back to caller.
     * @param uri, String[], String selection, Bundle, CancellationSignal
     * @return Cursor
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (uriMatcher.match(uri) == NOTES_ID){
            selection = DataBaseOpener.NOTE_ID + "=" + uri.getLastPathSegment();
        }

        return database.query(DataBaseOpener.TABLE_NOTES, DataBaseOpener.ALL_COLUMNS,
                selection, null, null, null,
                DataBaseOpener.NOTE_CREATED + " DESC");
    }

    /**
     * Returns MIMI type of data in content provider.
     * @param uri
     * @return String (MIMI data type)
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * Inserts new data into content provider.
     * @param uri, ContentValue
     * @return Uri
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = database.insert(DataBaseOpener.TABLE_NOTES, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    /**
     * Deletes data from content provider.
     * @param uri, String value, String[]
     * @return Integer
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return database.delete(DataBaseOpener.TABLE_NOTES, selection, selectionArgs);
    }

    /**
     * Updates existing data in content provider.
     * @param uri, ContentValue, String value, String[]
     * @return Integer
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return database.update(DataBaseOpener.TABLE_NOTES, values, selection, selectionArgs);
    }
}
