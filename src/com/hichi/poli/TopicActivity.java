package com.hichi.poli;

/**
 * Derivative Authors: _ Original Authors: Copyright (C) 2012 Wglxy.com
 * Originally licensed under the Apache License, Version 2.0:
 * http://www.apache.org/licenses/LICENSE-2.0
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.hichi.poli.data.Tile;

/**
 * Displays a topic's extended view. Since this is a demo, the view contains
 * only sample text. The name of the topic comes from the TopicList class.
 * @author William Gaul
 */
public class TopicActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_topic);
        Intent in = getIntent();
        String key = in.getStringExtra("key");
        
        //Tile t = TestTiles.getTile(key);
        Tile t = null; //@TODO: This
        String title = "";
        String body = "";

        TextView tv = (TextView) findViewById(R.id.title);
        if (tv != null) {
            tv.setText(title);
            tv.setTag(key);
        }

        TextView tv2 = (TextView) findViewById(R.id.body);
        if (tv2 != null) {
            tv2.setText(body);
            tv2.setTag(key);
        }
    }
}