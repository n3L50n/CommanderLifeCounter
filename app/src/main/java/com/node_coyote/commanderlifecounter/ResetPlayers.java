package com.node_coyote.commanderlifecounter;

/**
 * Created by node_coyote on 4/17/17.
 */

import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;

/**
 * Reset class to handle all reset methods
 */
public class ResetPlayers implements LoaderManager.LoaderCallbacks, AdapterView.OnItemClickListener {

    private boolean mResetHasBeenClicked = false;

//        public class ResetPlayer() {
//            //
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Check for changes, if none return

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }



}
