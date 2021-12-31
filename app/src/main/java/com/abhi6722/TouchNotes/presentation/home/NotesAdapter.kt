package com.abhi6722.TouchNotes.presentation.home

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhi6722.TouchNotes.R
import com.abhi6722.TouchNotes.entities.Notes
import kotlinx.android.synthetic.main.item_rv_notes.view.*

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var arrList = ArrayList<Notes>()
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        holder.itemView.tvTitle.text = arrList[position].title
        holder.itemView.tvDesc.text = arrList[position].noteText
        holder.itemView.tvDateTime.text = arrList[position].dateTime

        if (arrList[position].color != null) {
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(arrList[position].color))
        } else {
            null
        }

        if (arrList[position].imgPath != null) {
            holder.itemView.imgNote.setImageBitmap(BitmapFactory.decodeFile(arrList[position].imgPath))
            holder.itemView.imgNote.visibility = View.VISIBLE
        } else {
            holder.itemView.imgNote.visibility = View.GONE
        }

        if (arrList[position].webLink != null) {
            holder.itemView.tvWebLink.text = arrList[position].webLink
            holder.itemView.tvWebLink.visibility = View.VISIBLE
        } else {
            holder.itemView.tvWebLink.visibility = View.GONE
        }

        holder.itemView.cardView.setOnClickListener {
            listener!!.onClicked(arrList[position].id!!)
        }
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setData(arrNotesList: List<Notes>) {
        arrList = arrNotesList as ArrayList<Notes>
    }

    fun setOnClickListener(listener1: OnItemClickListener) {
        listener = listener1
    }

    inner class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun onClicked(notesId: Int)
    }
}
