package com.node_coyote.commanderlifecounter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by node_coyote on 4/19/17.
 */

public class DetailLifeFragment extends Fragment {

    /**
     * Creates a new instance of DetailLifeFragment() with num argument
     *
     * @return the new card
     */
    public DetailLifeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_life_frag, container, false);

        TextView lifeTotal = (TextView) rootView.findViewById(R.id.opponent_life_text_view);
        lifeTotal.setText("20");
        
        return rootView;

    }
}
