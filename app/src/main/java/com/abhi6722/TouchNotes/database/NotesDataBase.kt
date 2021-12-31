package com.abhi6722.TouchNotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abhi6722.TouchNotes.dao.NoteDao
import com.abhi6722.TouchNotes.entities.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDataBase : RoomDatabase() {

    companion object {

        private var notesDataBase: NotesDataBase? = null

        @Synchronized
        fun getDataBase(context: Context): NotesDataBase {

            if (notesDataBase == null) {
                notesDataBase = Room.databaseBuilder(context, NotesDataBase::class.java, "notes.db").build()
            }
            return notesDataBase!!
        }
    }

    abstract fun noteDao(): NoteDao
}
