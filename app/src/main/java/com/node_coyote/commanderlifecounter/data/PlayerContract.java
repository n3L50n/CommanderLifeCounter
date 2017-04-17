package com.node_coyote.commanderlifecounter.data;

/**
 * Created by node_coyote on 4/17/17.
 */

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract for the Command Zone app
 */
public final class PlayerContract {

    /**
     * Empty constructor. Don't instantiate this class.
     */
    public PlayerContract(){}

    /**
     * Content Authority for content provider
     */
    public static final String CONTENT_AUTHORITY = "com.node_coyote.commanderlifecounter";

    /**
     * Base uri for apps to contact the content provider
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Append path to base content uri to dig into the players database table
     */
    public static final String PATH_PLAYER = "players";

    /**
     * This inner class holds the constant values for the players database table
     */
    public static final class PlayerEntry implements BaseColumns {

        /**
         * Content uri to access players data in the content provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PLAYER);

        /**
         * MIME type for a list of players {@link #CONTENT_URI}
         */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYER;

        /**
         * MIME type for a single player {@link #CONTENT_URI}
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYER;

        /**
         * name of database table for players
         */
        public static final String TABLE_NAME = "players";

        /**
         * Type: INTEGER
         * Unique identifier for a player (row) in the database
         */
        public static final String _ID = BaseColumns._ID;

        /**
         * Type: TEXT
         * An optional name for a player (default Player One etc...)
         */
        public static final String COLUMN_PLAYER_NAME = "name";

        /**
         * Type: INTEGER
         * The current life total of the player
         */
        public static final String COLUMN_PLAYER_LIFE = "life";

        /**
         * Type: INTEGER
         * The optional current life total of the owner's commander damage on other players
         */
        public static final String COLUMN_PLAYER_COMMANDER_LIFE = "commander";

        /**
         * Type: INTEGER
         * The optional current energy total of a player
         */
        public static final String COLUMN_PLAYER_ENERGY = "energy";

        /** Type: INTEGER
         * The optional current poison total of a player
         */
        public static final String COLUMN_PLAYER_POISON = "poison";

        /** Type: INTEGER
         * The optional current experience counters of a player
         */
        public static final String COLUMN_PLAYER_EXPERIENCE = "experience";

        /**
         * Type: INTEGER
         * The total games won associated with a player's unique name
         */
        public static final String COLUMN_PLAYER_WON = "won";

        /**
         * Type: INTEGER
         * The total games lost associated with a player's unique name
         */
        public static final String COLUMN_PLAYER_LOST = "lost";

    }


}
