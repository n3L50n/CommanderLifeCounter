package com.node_coyote.commanderlifecounter;

import android.content.ContentValues;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.node_coyote.commanderlifecounter.data.PlayerContract;

/**
 * Created by node_coyote on 4/20/17.
 */

public class CommanderRecyclerAdapter extends RecyclerView.Adapter<CommanderRecyclerAdapter.ViewHolder> {
    private String[] mDataset;
    private CommanderRecyclerAdapter.ViewHolder mHolder;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;
        public TextView mLifeTextView;
        public Button mPlusLifeButton;
        public Button mMinusLifeButton;
        public TextView mCommanderLifeTextView;
        public Button mCommanderLifeButton;
        public Button mFocusCommanderTax;
        public ViewHolder(CardView v, TextView t, TextView c, Button p, Button m, Button l, Button f) {
            super(v);
            mCommanderLifeTextView = c;
            mCardView = v;
            mLifeTextView = t;
            mPlusLifeButton = p;
            mMinusLifeButton = m;
            mCommanderLifeButton = l;
            mFocusCommanderTax = f;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CommanderRecyclerAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CommanderRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        CardView a = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_life_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        final TextView b = (TextView) a.findViewById(R.id.opponent_life_text_view);
        final TextView c = (TextView) a.findViewById(R.id.commander_detail_life_text_view);
        Button d = (Button) a.findViewById(R.id.single_opponent_life_add_button);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int lifeTotal = Integer.parseInt(b.getText().toString());

                if (!TextUtils.isEmpty(String.valueOf(lifeTotal))){
                    lifeTotal++;
                }

                String newLifeTotal = Integer.toString(lifeTotal);

                b.setText(newLifeTotal);

                ContentValues values = new ContentValues();
                values.put(PlayerContract.PlayerEntry.COLUMN_PLAYER_LIFE, newLifeTotal);
            }
        });
        Button e = (Button) a.findViewById(R.id.single_opponent_life_subtract_button);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int lifeTotal = Integer.parseInt(b.getText().toString());

                if (!TextUtils.isEmpty(String.valueOf(lifeTotal))){
                    lifeTotal--;
                }

                String newLifeTotal = Integer.toString(lifeTotal);

                b.setText(newLifeTotal);

                ContentValues values = new ContentValues();
                values.put(PlayerContract.PlayerEntry.COLUMN_PLAYER_LIFE, newLifeTotal);
            }
        });
        Button f = (Button) a.findViewById(R.id.add_subtract_commander_damage);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lifeTotal = Integer.parseInt(c.getText().toString());

                if (!TextUtils.isEmpty(String.valueOf(lifeTotal))){
                    lifeTotal++;
                }

                String newLifeTotal = Integer.toString(lifeTotal);

                c.setText(newLifeTotal);

                ContentValues values = new ContentValues();
                values.put(PlayerContract.PlayerEntry.COLUMN_PLAYER_LIFE, newLifeTotal);
            }
        });
        f.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int lifeTotal = Integer.parseInt(c.getText().toString());

                if (!TextUtils.isEmpty(String.valueOf(lifeTotal))){
                    lifeTotal--;
                }

                String newLifeTotal = Integer.toString(lifeTotal);

                c.setText(newLifeTotal);

                ContentValues values = new ContentValues();
                values.put(PlayerContract.PlayerEntry.COLUMN_PLAYER_LIFE, newLifeTotal);
                return true;
            }
        });
        final Button g = (Button) a.findViewById(R.id.opponent_commander_tax);
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int commanderTax = Integer.parseInt(g.getText().toString());

                if (!TextUtils.isEmpty(String.valueOf(commanderTax))) {
                    commanderTax = commanderTax + 2;
                }

                String newCommanerTax = Integer.toString(commanderTax);

                g.setText("+" + newCommanerTax);
            }
        });

        g.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int commanderTax = Integer.parseInt(g.getText().toString());

                if (!TextUtils.isEmpty(String.valueOf(commanderTax))) {
                    if (commanderTax <= 0) {
                        commanderTax = 0;
                    } else {
                        commanderTax = commanderTax - 2;
                    }
                }

                String newCommanderTax = Integer.toString(commanderTax);

                g.setText("+" + newCommanderTax);

                return true;
            }
        });

        mHolder = new CommanderRecyclerAdapter.ViewHolder(a, b, c, d, e, f, g);

        return mHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CommanderRecyclerAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        mHolder.mLifeTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
