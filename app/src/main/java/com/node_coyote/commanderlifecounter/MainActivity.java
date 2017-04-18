package com.node_coyote.commanderlifecounter;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.node_coyote.commanderlifecounter.data.PlayerContract.PlayerEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * Identifier for the player data loader. Can be arbitrary.
     */
    private static final int EXISTING_PLAYER_LOADER = 42;

    /**
     * Boolean value to listen for
     */
    private boolean mResetClicked = false;

    /**
     * A text view for the life total of the player currently in the focus container
     */
    private TextView mFocusLifeTotal;

    /**
     * Uri to hold the current Player uri
     */
    private Uri mCurrentPlayerUri;

    private EditText mNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        mCurrentPlayerUri = intent.getData();

        mFocusLifeTotal = (TextView) findViewById(R.id.focus_life_text_view);
        if (TextUtils.isEmpty(mFocusLifeTotal.getText())){
            mFocusLifeTotal.setText(R.string.focus_default_life_total);
        }

        mNameEditText = (EditText) findViewById(R.id.name_edit_text);

        if (mCurrentPlayerUri == null){
            Log.v(LOG_TAG, "YOU LOSE! YOU GET NOTHING");
        } else {
            //TODO Load at correct time
            getLoaderManager().initLoader(EXISTING_PLAYER_LOADER, null, this);
        }

        // Add one to the focus players life total
        Button addFocusLife = (Button) findViewById(R.id.focus_life_add_button);
        addFocusLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 // Pull out the current life total

                int lifeTotal = Integer.parseInt(mFocusLifeTotal.getText().toString());

                Log.v(LOG_TAG, "Before" + String.valueOf(lifeTotal));


                if (!TextUtils.isEmpty(String.valueOf(lifeTotal))){
                    lifeTotal ++;
                }

                Log.v(LOG_TAG, "After" + String.valueOf(lifeTotal));


                String newLifeTotal = Integer.toString(lifeTotal);
                Log.v(LOG_TAG, String.valueOf(newLifeTotal));

                mFocusLifeTotal.setText(newLifeTotal);

                ContentValues values = new ContentValues();
                values.put(PlayerEntry.COLUMN_PLAYER_LIFE, newLifeTotal);

                if (mCurrentPlayerUri != null){
                    mCurrentPlayerUri = getContentResolver().insert(mCurrentPlayerUri, values);
                }

            }
        });

        // Subtract one from the focus players life total
        Button subtractFocusLife = (Button) findViewById(R.id.focus_life_subtract_button);
        subtractFocusLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Pull out the current life total

                int lifeTotal = Integer.parseInt(mFocusLifeTotal.getText().toString());

                Log.v(LOG_TAG, "Before" + String.valueOf(lifeTotal));


                if (!TextUtils.isEmpty(String.valueOf(lifeTotal))){
                    lifeTotal --;
                }

                Log.v(LOG_TAG, "After" + String.valueOf(lifeTotal));


                String newLifeTotal = Integer.toString(lifeTotal);
                Log.v(LOG_TAG, String.valueOf(newLifeTotal));

                mFocusLifeTotal.setText(newLifeTotal);

                ContentValues values = new ContentValues();
                values.put(PlayerEntry.COLUMN_PLAYER_LIFE, newLifeTotal);

                if (mCurrentPlayerUri != null){
                    mCurrentPlayerUri = getContentResolver().insert(mCurrentPlayerUri, values);
                }

            }
        });

        // TODO move dialog logic out of onCreate if possible
        ImageButton resetButton = (ImageButton) findViewById(R.id.reset_image_view);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResetClicked){
                    return;
                } else {
                    //If so lets pop up a dialog to verify a possible winner
                    DialogInterface.OnClickListener resetDialog = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //
                        }
                    };
                    showResetDialog(resetDialog);
                }
            }
        });

        // TODO Remove from onCreate for lazier loading. Not necessary in first load?
        ViewStub poisonEnergyExperienceContainer = (ViewStub) findViewById(R.id.poison_experience_energy_stub);
        View topContainer =  poisonEnergyExperienceContainer.inflate();
        topContainer.setVisibility(View.INVISIBLE);
    }


    // TODO pull out to a lazier loading. Hopefully currently unused ResetPlayers class
    public void showResetDialog(DialogInterface.OnClickListener resetButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.reset_dialog_default_message);

        // Positive should update values as is then reset them
        builder.setPositiveButton(R.string.reset_dialog_yes, resetButtonClickListener);

        // Negative should not update values but instead return user to Main Activity
        // This will give a user a chance to update the values to reflect a winner and loser
        builder.setNegativeButton(R.string.reset_dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Neutral should update values as is then reset them
        builder.setNeutralButton(R.string.reset_dialog_dont_care, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null){
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                PlayerEntry._ID,
                PlayerEntry.COLUMN_PLAYER_NAME,
                PlayerEntry.COLUMN_PLAYER_LIFE,
                PlayerEntry.COLUMN_PLAYER_COMMANDER_LIFE,
                PlayerEntry.COLUMN_PLAYER_ENERGY,
                PlayerEntry.COLUMN_PLAYER_EXPERIENCE,
                PlayerEntry.COLUMN_PLAYER_POISON,
                PlayerEntry.COLUMN_PLAYER_WON,
                PlayerEntry.COLUMN_PLAYER_LOST
        };

        return new CursorLoader(this,
                mCurrentPlayerUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        //TODO Why? Let's leave early if we have an empty cursor
        if (cursor == null && cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {

            // Find columns with player attributes
            int nameColumnIndex = cursor.getColumnIndex(PlayerEntry.COLUMN_PLAYER_NAME);

            String name = cursor.getString(nameColumnIndex);

            // Update UI
            mNameEditText.setText(name);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFocusLifeTotal.setText(R.string.focus_default_life_total);
    }
}
