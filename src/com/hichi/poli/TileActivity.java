package com.hichi.poli;

/**
 * Derivative Authors: _ Original Authors: Copyright (C) 2012 Wglxy.com
 * Originally licensed under the Apache License, Version 2.0:
 * http://www.apache.org/licenses/LICENSE-2.0
 */
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import com.androidquery.AQuery;
import com.hichi.poli.data.TileContentProvider;
import com.hichi.poli.data.TileTable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An activity that shows a single tile, with functionality that supports
 * touching the tile (opens extended view).
 * 
 * @author William Gaul
 */
public class TileActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    
    private int key;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_pager_grid_item);
        key = getIntent().getIntExtra("key", 1);
        
        // Load the tile information from the DB via the tile's ID (key)
        this.getSupportLoaderManager().initLoader(0, null, this);
    }

    public void showTopic() {
        Intent intent = new Intent(this.getApplicationContext(), TopicActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                TileContentProvider.CONTENT_URI.buildUpon().appendPath(Integer.toString(key)).build(),
                TileTable.getProjectableSchema(), null, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor d) {
        d.moveToFirst();
        String data = d.getString(d.getColumnIndex(TileTable.COLUMN_DATA));
        AQuery aq = new AQuery(this);
        try {
            JSONObject json = new JSONObject(data);
            aq.id(R.id.title).text(json.getString("title"));
            aq.id(R.id.title).clicked(new OnClickListener() {
                public void onClick(View view) {
                    showTopic();
                }
            });
            aq.id(R.id.image).background(R.color.background_grid1_cell);
            aq.id(R.id.image).image(json.getString("img"));
            aq.id(R.id.image).clicked(new OnClickListener() {
                public void onClick(View view) {
                    showTopic();
                }
            });
        } catch (JSONException ex) {
            //
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // Do nothing, for now
    }
}