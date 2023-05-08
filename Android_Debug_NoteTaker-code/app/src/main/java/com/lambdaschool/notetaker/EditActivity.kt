package com.lambdaschool.notetaker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class EditActivity : AppCompatActivity() {

    internal lateinit var editTitle: EditText
    internal lateinit var editContent: EditText
    internal var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.onActivityCreateSetTheme(this)
        setContentView(R.layout.activity_edit)

        editTitle = findViewById(R.id.edit_title)
        editContent = findViewById(R.id.edit_content)

        note = intent.getSerializableExtra("editNote") as Note
        if (note == null) {
            note = Note(Note.NO_ID)
        }

        editTitle.setText(note!!.title)
        editContent.setText(note!!.content)
    }

    override fun setTheme(resid: Int) {
        super.setTheme(resid)
    }

    override fun onBackPressed() {
        prepResult()
        super.onBackPressed()
    }

    private fun prepResult() {
        note!!.title = editTitle.text.toString()
        note!!.content = editContent.text.toString()
        val resultIntent = Intent()
        resultIntent.putExtra(EDIT_NOTE_KEY, note)
        setResult(Activity.RESULT_OK, resultIntent)
    }

    companion object {

        val EDIT_NOTE_KEY = "edit_note"
    }
}
