package com.node_coyote.commanderlifecounter.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.node_coyote.commanderlifecounter.data.PlayerContract.PlayerEntry;

/**
 * Created by node_coyote on 4/17/17.
 */

public class PlayerProvider extends ContentProvider {

    /**
     * Tag for log message
     */
    public static final String LOG_TAG = PlayerProvider.class.getSimpleName();

    /**
     * Uri matcher code for the content uri for the players table.
     * (Can be an arbitrary number)
     */
    private static final int PLAYERS = 42;

    /**
     * Uri matcher code for the content uri for a single player in the players table.
     * (Can be an arbitrary number)
     */
    private static final int PLAYERS_ID = 9;

    /**
     * Uri matcher to match a content uri to a code
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(PlayerContract.CONTENT_AUTHORITY, PlayerContract.PATH_PLAYER, PLAYERS);
        sUriMatcher.addURI(PlayerContract.CONTENT_AUTHORITY, PlayerContract.PATH_PLAYER, PLAYERS_ID);
    }

    /**
     * A database helper object
     */
    private PlayerDatabaseHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new PlayerDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // We need a readable database
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();

        Cursor cursor;

        // match the uri code
        int match = sUriMatcher.match(uri);

        switch (match) {
            case PLAYERS:

                // query the players table directly
                cursor = database.query(PlayerEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PLAYERS_ID:

                // query a single player (a row by id)
                selection = PlayerEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(PlayerEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown uri" + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLAYERS:
                return PlayerEntry.CONTENT_LIST_TYPE;
            case PLAYERS_ID:
                return PlayerEntry.CONTENT_LIST_TYPE;
            default:
                throw new IllegalArgumentException("Unknown uri " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        // insert a new player into the players table
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLAYERS:
                return insertPlayer(uri, values);
            case PLAYERS_ID:
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Helper method to insert a new player into the players database
     *
     * @param uri    The given uri of the players database
     * @param values The given values to fill in for a specific row
     * @return new content uri for that specific row in the database
     */
    public Uri insertPlayer(Uri uri, ContentValues values) {

        // Let's check if the players name is null
        String playerName = values.getAsString(PlayerEntry.COLUMN_PLAYER_NAME);
        if (playerName == null) {
            throw new IllegalArgumentException("Players must have a name. Default Player + _ID");
        }

        // Let's check if the life value is null
        Integer playerLife = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_LIFE);
        if (playerLife == null) {
            throw new IllegalArgumentException("Players must have a life. Default 20 ");
        }

        // Let's check if the commander life value is null
        Integer commanderLife = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_COMMANDER_LIFE);
        if (commanderLife == null) {
            throw new IllegalArgumentException("Players must have commander life. Default 40");
        }

        // Let's check if the energy value is null
        Integer energy = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_ENERGY);
        if (energy == null) {
            throw new IllegalArgumentException("Players must have energy total. Default 0");
        }

        // Let's check if the experience value is null
        Integer experience = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_EXPERIENCE);
        if (experience == null) {
            throw new IllegalArgumentException("Players must have experience total. Default 0");
        }

        // Let's check if the poison value is null
        Integer poison = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_POISON);
        if (poison == null) {
            throw new IllegalArgumentException("Players must have poison total. Default 0");
        }

        Integer won = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_WON);
        if (won == null) {
            throw new IllegalArgumentException("Players must have a number of wins. Default 0");
        }

        Integer lost = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_LOST);
        if (lost == null) {
            throw new IllegalArgumentException("Players must have a number of losses. Default 0");
        }

        // Get a writable database
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        // insert new player with the given values
        long id = database.insert(PlayerEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.v(LOG_TAG, "Failed to insert row " + uri);
            return null;
        }

        // Notify listeners of change
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        // Get the writable database
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        // Track the number of delete rows
        int deletedRows;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLAYERS:
                // delete all the rows. Careful with this one!
                deletedRows = database.delete(PlayerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PLAYERS_ID:
                // Delete a single row given by an id in the uri
                selection = PlayerEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                deletedRows = database.delete(PlayerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion not supported for " + uri);
        }

        if (deletedRows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        // Let's check if this case is updating the whole database or just a row
        switch (match) {
            case PLAYERS:
                return updatePlayer(uri, values, selection, selectionArgs);
            case PLAYERS_ID:
                // Let's pull out the uri so we know which row to update
                selection = PlayerEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePlayer(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Helper method for update with equivalent parameters.
     * @param uri We need the uri for the content
     * @param values we need the values of the content
     * @param selection We need an optional but likely selection option
     * @param selectionArgs We need the optional but likely selection arguments
     * @return The number of updated rows
     */
    public int updatePlayer(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // Let's validate our values
        if (values.containsKey(PlayerEntry.COLUMN_PLAYER_NAME)) {
            String name = values.getAsString(PlayerEntry.COLUMN_PLAYER_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Players require a name. Default Player + _ID");
            }
        }

        if (values.containsKey(PlayerEntry.COLUMN_PLAYER_LIFE)) {
            Integer life = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_LIFE);
            if (life == null) {
                throw new IllegalArgumentException("Players require a life. Default 20");
            }
        }

        if (values.containsKey(PlayerEntry.COLUMN_PLAYER_COMMANDER_LIFE)) {
            Integer commanderLife = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_COMMANDER_LIFE);
            if (commanderLife == null) {
                throw new IllegalArgumentException("Players require a commander life value. Default 40");
            }
        }

        if (values.containsKey(PlayerEntry.COLUMN_PLAYER_ENERGY)) {
            Integer energy = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_ENERGY);
            if (energy == null) {
                throw new IllegalArgumentException("Players require an energy total. Default 0");
            }
        }

        if (values.containsKey(PlayerEntry.COLUMN_PLAYER_EXPERIENCE)) {
            Integer experience = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_EXPERIENCE);
            if (experience == null) {
                throw new IllegalArgumentException("Players require an experience total. Default 0");
            }
        }

        if (values.containsKey(PlayerEntry.COLUMN_PLAYER_POISON)) {
            Integer poison = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_POISON);
            if (poison == null) {
                throw new IllegalArgumentException("Players require a poison total. Default 0");
            }
        }

        if (values.containsKey(PlayerEntry.COLUMN_PLAYER_WON)) {
            Integer won = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_WON);
            if (won == null) {
                throw new IllegalArgumentException("Players require a number of wins. Default 0");
            }
        }

        if (values.containsKey(PlayerEntry.COLUMN_PLAYER_LOST)) {
            Integer lost = values.getAsInteger(PlayerEntry.COLUMN_PLAYER_LOST);
            if (lost == null) {
                throw new IllegalArgumentException("Players require a number of losses. Default 0");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        // Let's get the database to write to
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        int updatedRows = database.update(PlayerEntry.TABLE_NAME, values, selection, selectionArgs);

        if (updatedRows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updatedRows;


    }
}


