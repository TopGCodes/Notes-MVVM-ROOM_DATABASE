package com.example.notes.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Models.Note
import com.example.notes.R
import kotlin.random.Random

class notesAdapter(
    private val context: Context,
    var Mylistener: Noteitemclicklistener,
    val deleteClicklistener: OnDeleteclicklistener
) :
    RecyclerView.Adapter<notesAdapter.NotesViewHolder>() {

    var NotesList = ArrayList<Note>()
    var fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.eachnote, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        var currentitem = NotesList.get(position)
        holder.title.text = currentitem.title
        holder.title.isSelected = true
        holder.note.text = currentitem.Note
        holder.timeOrdate.text = currentitem.date
        holder.timeOrdate.isSelected = true

        holder.card_layout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomColorGenetator(),
                null
            )
        )




        holder.card_layout.setOnClickListener {
            Mylistener.onItemClick(NotesList.get(position))
        }
        holder.deletNote?.setOnClickListener {
            if(holder.deletNote != null)
            {
                deleteClicklistener.onDeleteClick(currentitem)
            }

        }


    }

    override fun getItemCount(): Int {
        return NotesList.size
    }

    fun updatelist(newlist: List<Note>) {
        fullList.clear()
        fullList.addAll(newlist)

        NotesList.clear()
        NotesList.addAll(fullList)

        notifyDataSetChanged()
    }

    fun filterlist(search: String) {
        NotesList.clear()
        for (item in fullList) {

            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.Note?.lowercase()?.contains(search.lowercase()) == true
            ) {
                NotesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun randomColorGenetator(): Int {
        var colorlist = ArrayList<Int>()

        colorlist.add(R.color.Notecolor1)
        colorlist.add(R.color.Notecolor2)
        colorlist.add(R.color.Notecolor3)
        colorlist.add(R.color.Notecolor4)
        colorlist.add(R.color.Notecolor5)
        colorlist.add(R.color.Notecolor6)

        var seed = System.currentTimeMillis().toInt()
        var randomindex = Random(seed).nextInt(colorlist.size)
        return colorlist[randomindex]

    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card_layout = itemView.findViewById<CardView>(R.id.card_layout)
        var deletNote = itemView.findViewById<ImageView>(R.id.deleteNote)
        var title = itemView.findViewById<TextView>(R.id.Title)
        var note = itemView.findViewById<TextView>(R.id.Note)
        var timeOrdate = itemView.findViewById<TextView>(R.id.Time)


    }

    interface Noteitemclicklistener {
        // This method is when user click on the note it means user wants to update the note
        fun onItemClick(note: Note)
    }

    interface OnDeleteclicklistener {
        //this method is for when user clicks on delete image a note should delete
        fun onDeleteClick(note: Note)
    }
}
