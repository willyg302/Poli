package com.hichi.grid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import com.androidquery.AQuery;
import com.hichi.poli.R;
import com.hichi.poli.data.Tile;
import java.util.ArrayList;
import java.util.Arrays;
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
    
    private Typeface HC, HCL;

    public TileAdapter(Context c) {
        this(c, DEFAULT_CELL_SIZE, DEFAULT_CELL_SIZE);
    }

    public TileAdapter(Context c, int cellWidth, int cellHeight) {
        mContext = c;
        tileArray = new ArrayList<Tile>();
        mCellWidth = cellWidth;
        mCellHeight = cellHeight;
        
        HC = Typeface.createFromAsset(this.mContext.getAssets(), "fonts/Helvetica-Condensed.otf");
        HCL = Typeface.createFromAsset(this.mContext.getAssets(), "fonts/Helvetica-Condensed-Light.otf");
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
    
    private int getAppropriateLayout(String type) {
        switch (Arrays.asList(new String[] {"facebook", "twitter", "news", "youtube"}).indexOf(type)) {
            case 0:
                return R.layout.facebook_grid_item;
            case 1:
                return R.layout.twitter_grid_item;
            case 3:
                return R.layout.youtube_grid_item;
            default:
                return R.layout.demo_pager_grid_item;
        }
    }
    
    private void handleFacebookTile(String data, AQuery aq, int position) throws JSONException {
        JSONObject json = new JSONObject(data);
        aq.id(R.id.image).tag(Integer.valueOf(position));
        aq.id(R.id.title).text(json.getString("title")).textSize(8f).tag(Integer.valueOf(position)).typeface(HC).height(mCellHeight/4);
    }
    
    private void handleTwitterTile(String data, AQuery aq, int position) throws JSONException {
        JSONObject json = new JSONObject(data);
        aq.id(R.id.image).tag(Integer.valueOf(position));
        aq.id(R.id.title).text(json.getString("title")).textSize(8f).tag(Integer.valueOf(position)).typeface(HC).height(mCellHeight/4);
    }
    
    private void handleNewsTile(String data, AQuery aq, int position) throws JSONException {
        JSONObject json = new JSONObject(data);
        aq.id(R.id.image).tag(Integer.valueOf(position));
        aq.id(R.id.title).text(json.getString("title")).textSize(8f).tag(Integer.valueOf(position)).typeface(HC).height(mCellHeight/4);
    }
    
    private void handleYoutubeTile(String data, AQuery aq, int position) throws JSONException {
        JSONObject json = new JSONObject(data);
        aq.id(R.id.image).image(json.getString("img")).tag(Integer.valueOf(position));
        aq.id(R.id.title).text(json.getString("title")).textSize(8f).tag(Integer.valueOf(position)).typeface(HC).height(mCellHeight/4);
    }
    
    private void handleDefaultTile(String data, AQuery aq, int position) throws JSONException {
        JSONObject json = new JSONObject(data);
        aq.id(R.id.image).image(json.getString("img")).tag(Integer.valueOf(position));
        aq.id(R.id.title).text(json.getString("title")).textSize(8f).tag(Integer.valueOf(position)).typeface(HC).height(mCellHeight/4);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        String data = tileArray.get(position).getData();
        
        // Set appropriate layout based on tile type
        int layout = getAppropriateLayout(tileArray.get(position).getType());
        if (convertView == null) {
            convertView = ((Activity) mContext).getLayoutInflater().inflate(layout, null);
        }
        AQuery aq = new AQuery(convertView);
        convertView.setLayoutParams(new GridView.LayoutParams(mCellWidth, mCellHeight));
        
        try {
            switch (layout) {
                case R.layout.facebook_grid_item:
                    handleFacebookTile(data, aq, position);
                    break;
                case R.layout.twitter_grid_item:
                    handleTwitterTile(data, aq, position);
                    break;
                case R.layout.youtube_grid_item:
                    handleYoutubeTile(data, aq, position);
                default:
                    handleDefaultTile(data, aq, position);
                    break;
            }
        } catch (JSONException ex) {
            Log.d("DEBUG", "Error in TileAdapter: " + ex.toString());
        }
        return convertView;
    }
}