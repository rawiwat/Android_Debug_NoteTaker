package com.lambdaschool.notetaker

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(NotesDbContract.NotesEntry.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVer: Int, newVer: Int) {
        sqLiteDatabase.execSQL(NotesDbContract.NotesEntry.SQL_DELETE_TABLE)
        this.onCreate(sqLiteDatabase)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        this.onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        private val DATABASE_VERSION = 0
        private val DATABASE_NAME = "NotesDatabase.db"
    }
}
