package com.node_coyote.commanderlifecounter.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by node_coyote on 4/18/17.
 */

public class CommanderDatabaseHelper extends SQLiteOpenHelper {

    /**
     * Name of the database
     */
    private static final String DATABASE_NAME = "commanders.db";

    /**
     * Change this number whenever the database schema changes
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructor for use with a new instance of our helper
     * @param context
     */
    public CommanderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is first created
     * @param db The database to be created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // String to hold a SQL statement to create a database table
        String SQL_CREATE_PLAYERS_TABLE = "CREATE_TABLE" + PlayerContract.PlayerEntry.TABLE_NAME + " ("
                + PlayerContract.PlayerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PlayerContract.PlayerEntry.COLUMN_PLAYER_NAME + "TEXT NOT NULL, "
                + PlayerContract.PlayerEntry.COLUMN_PLAYER_LIFE + "INTEGER NOT NULL DEFAULT 20, "
                + PlayerContract.PlayerEntry.COLUMN_PLAYER_COMMANDER_LIFE + "INTEGER NOT NULL DEFAULT 40, "
                + PlayerContract.PlayerEntry.COLUMN_PLAYER_ENERGY + "INTEGER NOT NULL DEFAULT 0, "
                + PlayerContract.PlayerEntry.COLUMN_PLAYER_EXPERIENCE + "INTEGER NOT NULL DEFAULT 0, "
                + PlayerContract.PlayerEntry.COLUMN_PLAYER_WON + "INTEGER NOT NULL DEFAULT 0, "
                + PlayerContract.PlayerEntry.COLUMN_PLAYER_LOST + "INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_PLAYERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
