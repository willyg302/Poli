package com.hichi.poli.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * A helper class, our app only uses this to communicate with the DB.
 * 
 * NOTE: This class also provides functionality for copying our pre-generated
 * DB from the assets/databases folder when no in-memory DB exists by extending
 * from SQLiteAssetHelper. To force copying of a new DB into memory, clear
 * user data for this app from within Android.
 *
 * @author William Gaul
 */
public class TileDatabaseHelper extends SQLiteAssetHelper {
    
    private static final String DB_NAME = "tiletable";
    private static final int DB_VERSION = 1;
    
    public TileDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Called during an upgrade of the database, such as increase of DATABASE_VERSION
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        TileTable.onUpgrade(database, oldVersion, newVersion);
        // Do nothing else, for now!
    }
}