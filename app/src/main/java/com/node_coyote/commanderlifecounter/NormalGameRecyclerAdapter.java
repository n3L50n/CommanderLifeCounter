package com.node_coyote.commanderlifecounter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by node_coyote on 4/19/17.
 */

public class NormalGameRecyclerAdapter extends RecyclerView.Adapter<NormalGameRecyclerAdapter.ViewHolder> {
    private String[] mDataset;
    private NormalGameRecyclerAdapter.ViewHolder mHolder;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;
        public TextView mTextView;
        public TextView mCommanderTextView;
        public ViewHolder(CardView v, TextView t, TextView c) {
            super(v);
            mCommanderTextView = c;
            mCardView = v;
            mTextView = t;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NormalGameRecyclerAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NormalGameRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_life_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        TextView l = (TextView) v.findViewById(R.id.detail_life_text_view);

        // TODO maybe fit an optional poison / energy tracker here.
        TextView c = (TextView) v.findViewById(R.id.detail_open_counter);
        mHolder = new ViewHolder(v, l, c);
        return mHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        mHolder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}

