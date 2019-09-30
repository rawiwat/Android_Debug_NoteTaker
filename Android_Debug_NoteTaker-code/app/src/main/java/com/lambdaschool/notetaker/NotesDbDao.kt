package com.lambdaschool.notetaker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import java.util.ArrayList

object NotesDbDao {
    private var db: SQLiteDatabase? = null

    fun initializeInstance(context: Context) {
        if (db == null) {
            val helper = NotesDbHelper(context)
            db = helper.writableDatabase
        }
    }

    fun readNote(id: String): Note? {
        if (db != null) {
            val cursor = db!!.rawQuery(String.format("SELECT * FROM %s WHERE %s = '%s'",
                    NotesDbContract.NotesEntry.COLUMN_NAME_FB_ID,
                    id), null)
            val note: Note?
            if (cursor.moveToNext()) {
                note = getNoteFromCursor(cursor)
            } else {
                note = null
            }
            cursor.close()
            return note

        } else {
            return null
        }
    }

    private fun getNoteFromCursor(cursor: Cursor): Note {
        var index: Int
        val note: Note
        index = cursor.getColumnIndexOrThrow(NotesDbContract.NotesEntry.COLUMN_NAME_TITLE)
        val title = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(NotesDbContract.NotesEntry.COLUMN_NAME_CONTENT)
        val content = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(NotesDbContract.NotesEntry.COLUMN_NAME_FB_ID)
        val retrievedId = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(NotesDbContract.NotesEntry.COLUMN_NAME_TIMESTAMP)
        val timestamp = cursor.getLong(index)

        note = Note(title, content, retrievedId, timestamp)
        return note
    }

    fun readAllNotes(): ArrayList<Note> {
        if (db != null) {
            val cursor = db!!.rawQuery(String.format("SELECT * FROM %s;",
                    NotesDbContract.NotesEntry.TABLE_NAME), null)

            val notesList = ArrayList<Note>()
            while (cursor.moveToNext()) {
                notesList.add(getNoteFromCursor(cursor))
            }
            cursor.close()
            return notesList

        } else {
            return ArrayList()
        }
    }

    fun createNote(note: Note) {
        if (db != null) {
            val values = ContentValues()
            values.put(NotesDbContract.NotesEntry.COLUMN_NAME_CONTENT, note.content)
            values.put(NotesDbContract.NotesEntry.COLUMN_NAME_TIMESTAMP, note.timestamp)
            values.put(NotesDbContract.NotesEntry.COLUMN_NAME_TITLE, note.title)
            values.put(NotesDbContract.NotesEntry.COLUMN_NAME_FB_ID, note.getId())

            val resultId = db!!.insert(NotesDbContract.NotesEntry.TABLE_NAME, null, values)

            /*db.execSQL(String.format("INSERT INTO %s " +
                                      "(%s, %s, %s, %s) " +
                                      "VALUES ('%s', '%s', '%s', %s);",

                                      NotesDbContract.NotesEntry.TABLE_NAME,

                                      NotesDbContract.NotesEntry.COLUMN_NAME_FB_ID,
                                      NotesDbContract.NotesEntry.COLUMN_NAME_TITLE,
                                      NotesDbContract.NotesEntry.COLUMN_NAME_CONTENT,
                                      NotesDbContract.NotesEntry.COLUMN_NAME_TIMESTAMP,

                                      note.getId(),
                                      note.getTitle(),
                                      note.getContent(),
                                      note.getTimestamp()));*/
        }
    }

    fun updateNote(note: Note) {
        if (db != null) {
            val whereClause = String.format("%s = '%s'",
                    NotesDbContract.NotesEntry.COLUMN_NAME_FB_ID,
                    note.getId())

            val cursor = db!!.rawQuery(String.format("SELECT * FROM %s WHERE %s",
                    NotesDbContract.NotesEntry.TABLE_NAME,
                    whereClause), null)

            if (cursor.count == 1) {
                val values = ContentValues()
                values.put(NotesDbContract.NotesEntry.COLUMN_NAME_CONTENT, note.content)
                values.put(NotesDbContract.NotesEntry.COLUMN_NAME_TIMESTAMP, note.timestamp)
                values.put(NotesDbContract.NotesEntry.COLUMN_NAME_TITLE, note.title)

                val affectedRows = db!!.update(NotesDbContract.NotesEntry.TABLE_NAME, values, whereClause, null)
            }
        }
    }

    fun deleteNote(note: Note) {
        if (db != null) {
            val whereClause = String.format("%s = '%s'",
                    NotesDbContract.NotesEntry.COLUMN_NAME_FB_ID,
                    note.getId())

            val affectedRows = db!!.delete(NotesDbContract.NotesEntry.TABLE_NAME, whereClause, null)
        }
    }


    fun updateCache(fbNotes: ArrayList<Note>): ArrayList<Note> {
        // read all notes
        val cacheNotes = readAllNotes()

        // check each note
        for (fbNote in fbNotes) {
            var noteFound = false
            for (cacheNote in cacheNotes) {
                if (fbNote.getId() == cacheNote.getId()) {
                    // if note does exist, check for timestamp
                    if (fbNote.timestamp > cacheNote.timestamp) {
                        // if fb is newer update cache
                        updateNote(fbNote)
                    } else {
                        // else keep cache
                    }
                    noteFound = true
                }
            }
            if (!noteFound) {
                // if note doesn't exist in cache, add it
                createNote(fbNote)
            }
        }

        return readAllNotes()
    }
}
