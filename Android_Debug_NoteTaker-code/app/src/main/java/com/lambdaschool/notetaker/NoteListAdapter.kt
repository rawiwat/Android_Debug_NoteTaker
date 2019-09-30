package com.lambdaschool.notetaker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

class NoteListAdapter internal constructor(private val dataList: ArrayList<Note>, private val activity: Activity) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {
    private var context: Context? = null

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var noteTitle: TextView
        var noteContent: TextView
        var parentView: ViewGroup

        init {

            noteTitle = itemView.findViewById(R.id.showTitle)
            noteContent = itemView.findViewById(R.id.note_element_content)
            parentView = itemView.findViewById(R.id.note_element_parent_layout)
        }
    }

    fun replaceList(newData: ArrayList<Note>) {
        this.dataList.clear()
        this.dataList.addAll(newData)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        context = viewGroup.context
        val view = LayoutInflater.from(
                viewGroup.context)
                .inflate(
                        R.layout.note_element_layout,
                        viewGroup,
                        false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val data = dataList[i]

        viewHolder.noteTitle.text = data.title
        // I removed the content length limit to better take advantage of the staggered grid
        /*String content;
        if (data.getContent().length() > 30) {
            content = data.getContent().substring(0, 30) + "...";
        } else {
            content = data.getContent();
        }*/
        /*if(i == 1) {
            viewHolder.parentView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            viewHolder.parentView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }*/

        viewHolder.noteContent.text = data.content
        viewHolder.parentView.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(EditActivity.EDIT_NOTE_KEY, data)
            activity.startActivityForResult(intent, MainActivity.Companion.getEDIT_REQUEST_CODE())
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
