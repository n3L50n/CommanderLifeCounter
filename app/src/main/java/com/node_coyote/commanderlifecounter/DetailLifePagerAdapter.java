package com.node_coyote.commanderlifecounter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by node_coyote on 4/19/17.
 */

public class DetailLifePagerAdapter extends FragmentPagerAdapter {

    public DetailLifePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DetailLifeFragment();
            case 1:
                return new DetailLifeFragment();
            default:
                return new DetailLifeFragment();
        }
    }
}
