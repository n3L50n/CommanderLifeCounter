package com.node_coyote.commanderlifecounter;

import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks{

    private boolean mResetHasBeenClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button resetButton = (Button) findViewById(R.id.reset_image_view);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResetHasBeenClicked){
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
