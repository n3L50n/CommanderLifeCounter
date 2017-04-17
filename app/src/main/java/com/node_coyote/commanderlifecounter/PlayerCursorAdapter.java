package com.node_coyote.commanderlifecounter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by node_coyote on 4/17/17.
 */

public class PlayerCursorAdapter extends CursorAdapter {

    public PlayerCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_main, parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
