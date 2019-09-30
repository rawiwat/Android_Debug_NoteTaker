package com.lambdaschool.notetaker

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context

import java.util.ArrayList

class NoteViewModel : ViewModel() {
    private var noteList: MutableLiveData<ArrayList<Note>>? = null
    private var repo: NoteRepository? = null

    fun getNotesList(context: Context): LiveData<ArrayList<Note>>? {
        if (noteList == null) {
            loadList(context)
        }
        return noteList
    }

    private fun loadList(context: Context) {
        repo = NoteRepository()
        noteList = repo!!.getNotes(context)
    }

    fun addNote(note: Note, context: Context) {
        if (noteList != null) {
            repo!!.addNote(note)
            //            noteList = repo.getNotes(context);
        }
    }
}
