package com.hichi.grid;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Sets up GridFragment objects to be displayed by a ViewPager. Each
 * GridFragment can be seen as analogous to a "screen" or a "page".
 * 
 * The GridAdapter is responsible for determining what info to provide to
 * the GridFragment to display on that page.
 * 
 * It is also here that we grab stuff from the DB. Everything to be displayed
 * gets pulled into a local list, so updates must happen here as well.
 *
 * @author William Gaul
 */
public class GridAdapter extends FragmentStatePagerAdapter {

    public static final int DEFAULT_NUM_SCREENS = 2;

    public GridAdapter(FragmentManager fm, Resources res) {
        super(fm);
    }
    
    @Override
    public int getCount() {
        return DEFAULT_NUM_SCREENS;
    }
    
    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt("pageNum", position + 1);
        GridFragment f = new GridFragment();
        f.setArguments(args);
        return f;
    }
}