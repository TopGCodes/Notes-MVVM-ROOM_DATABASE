package com.example.notes.Database

import androidx.lifecycle.LiveData
import com.example.notes.Models.Note

class noteRepository(private val noteDAO : noteDAO) {

  var allNotes : LiveData<List<Note>> = noteDAO.getAllNotes()


    suspend fun insert(note : Note)
    {
        noteDAO.insert(note)
    }
    //delete func two
    suspend fun delete(note : Note)
    {
        noteDAO.delete(note)
    }
    //delete MEthod
   suspend fun update(note : Note)
   {
       noteDAO.update(note.id, note.title, note.Note)
   }
}