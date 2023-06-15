package com.example.notes.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.Database.NoteDatabase
import com.example.notes.Database.noteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteViewmodel(application : Application) : AndroidViewModel(application) {



  var allnotes : LiveData<List<Note>>
  var repository : noteRepository


   init {
       val dao = NoteDatabase.getDatabase(application).getNoteDao()
       repository = noteRepository(dao)
       allnotes = repository.allNotes
   }
    fun deleteNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }
    fun insertNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
    fun update(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }


}