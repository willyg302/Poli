package com.hichi.poli.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * A wrapper around the table in our SQLite DB that holds all the tiles.
 * This interfaces with the rest of the app through TileDatabaseHelper.java.
 * 
 * @author William Gaul
 */
public class TileTable {

    // The name of our database table
    public static final String TABLE_TILE = "tiles";
    
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_BOOKMARKED = "bookmarked";
    
    public static void onCreate(SQLiteDatabase database) {
        // Do nothing, for now
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(TileTable.class.getName(), "Upgrading database (" + oldVersion + " --> " + newVersion + ")");
        onCreate(database);
    }
    
    public static String[] getProjectableSchema() {
        return new String[] {
            COLUMN_ID,
            COLUMN_TYPE,
            COLUMN_DATA,
            COLUMN_BOOKMARKED
        };
    }
}