package com.node_coyote.commanderlifecounter.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.node_coyote.commanderlifecounter.data.PlayerContract.PlayerEntry;

/**
 * Created by node_coyote on 4/17/17.
 */

public class PlayerDatabaseHelper extends SQLiteOpenHelper {

    /**
     * Name of the database
     */
    private static final String DATABASE_NAME = "players.db";

    /**
     * Change this number whenever the database schema changes
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructor for use with a new instance of our helper
     * @param context
     */
    public PlayerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is first created
     * @param db The database to be created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // String to hold a SQL statement to create a database table
        String SQL_CREATE_PLAYERS_TABLE = "CREATE_TABLE" + PlayerEntry.TABLE_NAME + " ("
                + PlayerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PlayerEntry.COLUMN_PLAYER_NAME + "TEXT NOT NULL, "
                + PlayerEntry.COLUMN_PLAYER_LIFE + "INTEGER NOT NULL DEFAULT 0, "
                + PlayerEntry.COLUMN_PLAYER_COMMANDER_LIFE + "INTEGER NOT NULL DEFAULT 0, "
                + PlayerEntry.COLUMN_PLAYER_ENERGY + "INTEGER NOT NULL DEFAULT 0, "
                + PlayerEntry.COLUMN_PLAYER_EXPERIENCE + "INTEGER NOT NULL DEFAULT 0, "
                + PlayerEntry.COLUMN_PLAYER_POISON + "INTEGER NOT NULL DEFAULT 0, "
                + PlayerEntry.COLUMN_PLAYER_WON + "INTEGER NOT NULL DEFAULT 0, "
                + PlayerEntry.COLUMN_PLAYER_LOST + "INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_PLAYERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
