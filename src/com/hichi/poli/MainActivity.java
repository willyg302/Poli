package com.hichi.poli;

import com.hichi.grid.GridAdapter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.agimind.widget.SlideHolder;

/**
 * The main activity, controlling the pager that shows several pages of tiles.
 * The pages are created by a GridAdapter, and displayed by a ViewPager.
 * 
 * Horizontal scrolling of pages is supported by the Android compatibility
 * package, revision 4. If you touch the image for a topic, you see the image
 * full-screen. Touching that image takes you to the topic text.
 */
public class MainActivity extends FragmentActivity {
    
    public static final String APP_NAME = "poli";

    private GridAdapter mAdapter;
    private ViewPager mPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_pager);

        Resources res = getResources();
        
        initializeSidebar(res);

        // Initialize our view pager and adapter
        mAdapter = new GridAdapter(getSupportFragmentManager(), res);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        
        //mPager.setOnPageChangeListener(null); // @TODO: This!...?
    }
    
    private void addSidebarButton(Resources res, LinearLayout parent, int ImageID, int StringID) {
        ImageButton ib = (ImageButton) this.getLayoutInflater().inflate(R.layout.sidebar_button, parent, false);
        ib.setContentDescription(res.getString(StringID));
        ib.setImageResource(ImageID);
        parent.addView(ib);
    }
    
    private void initializeSidebar(Resources res) {
        // Set the sidebar to be always open and on the right
        SlideHolder sh = (SlideHolder) findViewById(R.id.slideHolder);
        sh.setAlwaysOpened(true);
        sh.setDirection(SlideHolder.DIRECTION_RIGHT);
        
        // Add sidebar ImageButtons
        LinearLayout sidebar = (LinearLayout) findViewById(R.id.sidebar);
        
        addSidebarButton(res, sidebar, R.drawable.home, R.string.btn_home);
        addSidebarButton(res, sidebar, R.drawable.search, R.string.btn_search);
        addSidebarButton(res, sidebar, R.drawable.my_elections, R.string.btn_my_elections);
        addSidebarButton(res, sidebar, R.drawable.mock_vote, R.string.btn_mock_vote);
        addSidebarButton(res, sidebar, R.drawable.compare, R.string.btn_compare);
        addSidebarButton(res, sidebar, R.drawable.stats, R.string.btn_stats);
        addSidebarButton(res, sidebar, R.drawable.whos_who, R.string.btn_whos_who);
    }
}