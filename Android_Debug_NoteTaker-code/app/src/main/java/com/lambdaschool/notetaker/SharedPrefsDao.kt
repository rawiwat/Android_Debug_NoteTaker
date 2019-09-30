package com.lambdaschool.notetaker

import android.content.SharedPreferences
import com.lambdaschool.notetaker.SharedPrefsDao.idsString

import java.util.ArrayList

object SharedPrefsDao {
    private val KEY_IDS = "key_ids"
    private val KEY_ID_PREFIX = "key_id_"
    private val NEXT_KEY_ID = "key_next_id"

    private val idsString: String
        get() {
            var keyIds = ""
            if (MainActivity.Companion.getPreferences() != null) {
                keyIds = MainActivity.Companion.getPreferences().getString(KEY_IDS, "")
            }
            return keyIds
        }

    private// keys are stored as CSV string
    val allIds: Array<String>
        get() = idsString.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

    val allNotes: ArrayList<Note>
        get() {
            val ids = allIds
            val notes = ArrayList<Note>(ids.size)
            for (id in ids) {
                if (id != "") {
                    notes.add(getNote(id))
                }
            }
            return notes
        }

    private val nextId: Int
        get() {
            var currentId = 0
            if (MainActivity.Companion.getPreferences() != null) {
                currentId = MainActivity.Companion.getPreferences().getInt(NEXT_KEY_ID, 0)
                val nextId = currentId + 1
                val editor = MainActivity.Companion.getPreferences().edit()
                editor.putInt(NEXT_KEY_ID, nextId)
                editor.apply()
            }
            return currentId
        }

    private fun getNote(id: String): Note? {
        var note: Note? = null
        if (MainActivity.Companion.getPreferences() != null) {
            val noteString = MainActivity.Companion.getPreferences().getString(KEY_ID_PREFIX + id, "")
            note = Note(noteString)
        }
        return note
    }

    fun setNote(note: Note) {
        if (note.getId() == Note.NO_ID) {
            note.setId(nextId)
        }
        val ids = allIds
        var exists = false
        for (id in ids) {
            if (id != "") {
                if (note.getId() == id) {
                    exists = true
                    break
                }
            }
        }

        if (!exists) {
            addId(note.getId())
        }

        addNote(note)
    }

    private fun addNote(note: Note) {
        val editor = MainActivity.Companion.getPreferences().edit()
        editor.putString(KEY_ID_PREFIX + note.getId()!!, note.toCsvString())
        editor.apply()
    }

    private fun addId(id: String?) {
        var idsString = idsString
        idsString = "$idsString,$id"
        val editor = MainActivity.Companion.getPreferences().edit()
        editor.putString(KEY_IDS, idsString.replace(" ", ""))
        editor.apply()
    }
}
