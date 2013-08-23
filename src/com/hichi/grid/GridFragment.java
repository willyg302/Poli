package com.hichi.grid;

/**
 * Derivative Authors: _ Original Authors: Copyright (C) 2012 Wglxy.com
 * Originally licensed under the Apache License, Version 2.0:
 * http://www.apache.org/licenses/LICENSE-2.0
 */
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import com.hichi.poli.R;
import com.hichi.poli.TileActivity;
import com.hichi.poli.data.Tile;
import com.hichi.poli.data.TileContentProvider;
import com.hichi.poli.data.TileTable;
import java.util.ArrayList;

/**
 * A GridFragment is essentially a SCREEN. There may be multiple screens in the
 * pager, each with multiple tiles. Each grid's tiles is managed by a
 * TileAdapter, which populates the grid.
 */
public class GridFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private int pageNum;
    private GridView mGridView;
    
    public static final int MAX_TILES_PER_SCREEN = 50;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        pageNum = (args != null) ? args.getInt("pageNum") : 0;
    }

    /**
     * When the activity is created, divide the usable space into columns and
     * put a grid of images in that area.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity a = getActivity();
        Resources res = a.getResources();

        mGridView = (GridView) getView().findViewById(R.id.gridview);

        DisplayMetrics metrics = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // From the resource files, determine how many rows and columns are to be displayed.
        final int numRows = res.getInteger(R.integer.grid_num_rows);
        final int numCols = res.getInteger(R.integer.grid_num_cols);

        // Figure out how much space is available for the N rows and M columns to be displayed.
        // We start with the root view for the fragment and adjust for the title, padding, etc.
        int titleHeight = res.getDimensionPixelSize(R.dimen.topic_title_height);
        int titlePadding = res.getDimensionPixelSize(R.dimen.topic_title_padding);
        int gridHSpacing = res.getDimensionPixelSize(R.dimen.image_grid_hspacing);
        int gridVSpacing = res.getDimensionPixelSize(R.dimen.image_grid_vspacing);
        int otherGridH = res.getDimensionPixelSize(R.dimen.other_grid_h);
        int otherGridW = res.getDimensionPixelSize(R.dimen.other_grid_w);

        int heightUsed = (numRows + 1) * gridVSpacing + (titleHeight + 2 * titlePadding) + otherGridH;
        int widthUsed = (numCols + 1) * gridHSpacing + otherGridW;

        int cellWidth = (metrics.widthPixels - widthUsed) / numCols;
        int cellHeight = (metrics.heightPixels - heightUsed) / numRows;

        if (mGridView == null) {
            Log.d("DEBUG", "Unable to locate the gridview.");
        } else {
            // Connect the gridview with an adapter that fills up the space.
            mGridView.setAdapter(new TileAdapter(a, cellWidth, cellHeight));
            mGridView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                    showTopic(i);
                }
            });

            // Arrange it so a long click on an item in the grid shows the topic associated with the image.
            mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> av, View view, int i, long l) {
                    bookmark(i);
                    return true;
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_pager_grid, container, false);
        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setTag(new Integer(pageNum));
        
        Resources res = this.getActivity().getResources();
        
        String label = res.getString(R.string.pg_untitled);
        switch (pageNum) {
            case 1:
                label = res.getString(R.string.pg_search);
                break;
            case 2:
                label = res.getString(R.string.pg_favorites);
                break;
            default:
                break;
        }

        // Set label text for the view
        View tv = view.findViewById(R.id.text);
        if (tv != null) {
            ((TextView) tv).setText(label);
        }

        // Hide the "no items" content until it is needed.
        View nc = view.findViewById(R.id.no_topics_text);
        if (nc != null) {
            nc.setVisibility(View.INVISIBLE);
        }
        
        // This actually populates the screen through the DB!!!
        this.getLoaderManager().initLoader(pageNum, null, this);
        
        return view;
    }

    /**
     * Start a TileActivity to display the tile of the given index.
     */
    private void showTopic(int index) {
        Tile topic = (Tile) ((TileAdapter) mGridView.getAdapter()).getItem(index);
        Intent intent = new Intent(getActivity().getApplicationContext(), TileActivity.class);
        intent.putExtra("key", topic.getID());
        startActivity(intent);
    }
    
    /**
     * When the tile of the given index is long-clicked on the home page, we
     * consider that a "bookmark" action. The tile will then appear in the
     * favorites page.
     * 
     * When the tile is long-clicked on the favorites page, it is removed.
     */
    private void bookmark(int index) {
        Tile topic = (Tile) ((TileAdapter) mGridView.getAdapter()).getItem(index);
        ContentValues values = new ContentValues();
        boolean bookmark = (pageNum == 1);
        values.put(TileTable.COLUMN_BOOKMARKED, bookmark);
        this.getActivity().getContentResolver().update(
                TileContentProvider.CONTENT_URI.buildUpon().appendPath(Integer.toString(topic.getID())).build(),
                values, null, null);
        Toast.makeText(this.getActivity().getApplicationContext(),
                "Tile has been " + (bookmark ? "added to" : "removed from") + " favorites",
                Toast.LENGTH_SHORT).show();
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case 1:
                // Home page
                return new CursorLoader(getActivity(),
                        TileContentProvider.CONTENT_URI,
                        TileTable.getProjectableSchema(),
                        null, null, null);
            case 2:
                // Bookmarks
                return new CursorLoader(getActivity(),
                        TileContentProvider.CONTENT_URI,
                        TileTable.getProjectableSchema(),
                        TileTable.COLUMN_BOOKMARKED + "!=?",
                        new String[] {"0"},
                        null);
            default:
                return null;
        }
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor d) {
        if (d != null && d.getCount() > 0) {
            ArrayList<Tile> newTiles = new ArrayList<Tile>();
            d.moveToFirst();
            int numTiles = Math.min(d.getCount(), MAX_TILES_PER_SCREEN);
            for (int i = 0; i < numTiles; i++) {
                int id = d.getInt(d.getColumnIndex(TileTable.COLUMN_ID));
                String type = d.getString(d.getColumnIndex(TileTable.COLUMN_TYPE));
                String data = d.getString(d.getColumnIndex(TileTable.COLUMN_DATA));
                boolean bookmarked = d.getInt(d.getColumnIndex(TileTable.COLUMN_BOOKMARKED)) != 0;
                newTiles.add(new Tile(id, type, data, bookmarked));
                d.moveToNext();
            }
            ((TileAdapter) mGridView.getAdapter()).setTileArray(newTiles);
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // Do nothing, for now
    }
}