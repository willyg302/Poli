package com.hichi.poli;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.androidquery.AQuery;
import com.hichi.poli.data.TileContentProvider;
import com.hichi.poli.data.TileTable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Displays an extended view of a tile. This is generally its URL source in
 * an embedded WebView, but may later be configured based on the media.
 * 
 * @author William Gaul
 */
public class SourceActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    
    private int key;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_view);
        key = getIntent().getIntExtra("key", 1);
        
        // Load the tile information from the DB via the tile's ID (key)
        this.getSupportLoaderManager().initLoader(0, null, this);
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
            WebView engine = aq.id(R.id.web_engine).getWebView();
            // This next line is IMPORTANT, otherwise URLs are directed to the browser app
            engine.setWebViewClient(new WebViewClient());
            engine.getSettings().setJavaScriptEnabled(true);
            engine.loadUrl(json.getString("src"));
        } catch (JSONException ex) {
            Log.d("DEBUG", "Error in SourceActivity: " + ex.toString());
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // Do nothing, for now
    }
}