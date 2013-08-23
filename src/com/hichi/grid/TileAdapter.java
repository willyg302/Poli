package com.hichi.grid;

/**
 * Derivative Authors: _ Original Authors: Copyright (C) 2012 Wglxy.com
 * Originally licensed under the Apache License, Version 2.0:
 * http://www.apache.org/licenses/LICENSE-2.0
 */
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import com.androidquery.AQuery;
import com.hichi.poli.R;
import com.hichi.poli.data.Tile;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An adapter that sets up an array of tiles for topics. It currently gets its
 * info from the TopicList, but could be altered to use a database instead.
 *
 * Used in GridFragment as it constructs a grid of tiles for display.
 * 
 * Uses JSON and asynchronous image loading libraries.
 *
 * @author William Gaul
 */
public class TileAdapter extends BaseAdapter {

    public static final int DEFAULT_CELL_SIZE = 220;
    private Context mContext;
    private int mCellWidth, mCellHeight;
    private ArrayList<Tile> tileArray;

    public TileAdapter(Context c) {
        this(c, DEFAULT_CELL_SIZE, DEFAULT_CELL_SIZE);
    }

    public TileAdapter(Context c, int cellWidth, int cellHeight) {
        mContext = c;
        tileArray = new ArrayList<Tile>();
        mCellWidth = cellWidth;
        mCellHeight = cellHeight;
    }
    
    public void setTileArray(ArrayList<Tile> newTileArray) {
        this.tileArray = newTileArray;
        this.notifyDataSetChanged();
    }

    public int getCount() {
        return tileArray.size();
    }

    public Object getItem(int position) {
        return tileArray.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.demo_pager_grid_item, null);
        }
        AQuery aq = new AQuery(convertView);
        String data = tileArray.get(position).getData();
        try {
            convertView.setLayoutParams(new GridView.LayoutParams(mCellWidth, mCellHeight));
            JSONObject json = new JSONObject(data);
            aq.id(R.id.image).image(json.getString("img")).tag(Integer.valueOf(position));
            aq.id(R.id.title).text(json.getString("title")).textSize(8f).tag(Integer.valueOf(position));
        } catch (JSONException ex) {
            //
        }
        return convertView;
    }
}