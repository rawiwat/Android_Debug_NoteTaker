package com.lambdaschool.notetaker

import android.util.Log

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

object NotesFirebaseDao {
    private val BASE_URL = "https://notesdemoproject.firebaseio.com/"
    private val NOTES = "notes/"
    private val URL_ENDING = ".json"
    private val USER_ID = ""

    private val USER_INFO = BASE_URL + USER_ID + URL_ENDING
    private val USER_NOTES = BASE_URL + USER_ID + NOTES + URL_ENDING
    private val USER_SPECIFIC_NOTE = "$BASE_URL$USER_ID$NOTES%s/$URL_ENDING" //use string.format to add id

    val notes: ArrayList<Note>
        get() {
            val notes = ArrayList<Note>()
            val result = NetworkAdapter.httpRequest(USER_NOTES, NetworkAdapter.GET)
            Log.i("Firebase DAO", result)
            try {
                val topLevel = JSONObject(result)
                val noteIds = topLevel.names()
                for (i in 0 until noteIds.length()) {
                    val name = noteIds.getString(i)
                    notes.add(Note(
                            topLevel.getJSONObject(name),
                            name)
                    )
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return notes
        }

    fun createNote(note: Note): String {
        val result = NetworkAdapter.httpRequest(USER_NOTES, NetworkAdapter.POST, note.toJsonString())

        try {
            return JSONObject(result).getString("name")
        } catch (e: JSONException) {
            e.printStackTrace()
            return ""
        }

    }

    fun deleteNote(id: String) {
        NetworkAdapter.httpRequest(String.format(USER_SPECIFIC_NOTE, id), NetworkAdapter.DELETE)
    }

    fun updateNote(id: String, newNote: Note) {
        NetworkAdapter.httpRequest(String.format(USER_SPECIFIC_NOTE, id), NetworkAdapter.PUT, newNote.toJsonString())
    }
}
