package com.lambdaschool.notetaker

import android.arch.lifecycle.MutableLiveData
import android.content.Context

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class NoteRepository {
    //    private ArrayList<Note> notes;
    var liveDataList: MutableLiveData<ArrayList<Note>>

    private val notesFromCache: ArrayList<Note>
        get() = NotesDbDao.readAllNotes()

    /*public NoteRepository() {
        this.notes = new ArrayList<>();
    }*/

    fun getNotes(context: Context): MutableLiveData<ArrayList<Note>> {
        liveDataList = MutableLiveData()
        NotesDbDao.initializeInstance(context)
        // retrieve notes from cache
        liveDataList.value = notesFromCache
        // retrieve notes from online DB
        Thread(Runnable {
            val notes = NotesFirebaseDao.notes

            val updatedNotes = NotesDbDao.updateCache(notes)

            liveDataList.postValue(updatedNotes)
        }).start()
        return liveDataList
    }

    fun addNote(note: Note) {
        Thread(Runnable {
            val newId = NotesFirebaseDao.createNote(note)
            note.setId(newId)
            NotesDbDao.createNote(note)
            liveDataList.postValue(notesFromCache)
        }).start()
        //        return SharedPrefsDao.getAllNotes();
    }
}
