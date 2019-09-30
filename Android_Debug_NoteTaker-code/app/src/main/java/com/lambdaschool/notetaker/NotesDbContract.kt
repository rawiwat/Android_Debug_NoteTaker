package com.lambdaschool.notetaker

import android.provider.BaseColumns

class NotesDbContract {
    class NotesEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "notes"

            val COLUMN_NAME_TITLE = "title"
            val COLUMN_NAME_FB_ID = "firebase_id"
            val COLUMN_NAME_CONTENT = "content"
            val COLUMN_NAME_TIMESTAMP = "timestamp"

            val SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                    " ( " +
                    BaseColumns._ID + " TEXT PRIMARY KEY, " +
                    COLUMN_NAME_FB_ID + " TEXT, " +
                    COLUMN_NAME_CONTENT + " TEXT, " +
                    COLUMN_NAME_TITLE + " TEXT, " +
                    COLUMN_NAME_TIMESTAMP + " INTEGER);"

            val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME;"
        }
    }
}
