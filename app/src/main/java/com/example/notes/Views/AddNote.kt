package com.example.notes.Views

import android.app.Activity
import android.content.Intent
import android.icu.text.CaseMap
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.Adapter.notesAdapter
import com.example.notes.Models.Note
import com.example.notes.Models.NoteViewmodel
import com.example.notes.R
import com.example.notes.databinding.ActivityAddNoteBinding
import java.util.*


class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var viewModel: NoteViewmodel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()




        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewmodel::class.java)


        val noteType = intent.getStringExtra("noteType")
        var noteIdpassed = intent.getIntExtra("noteId",1)
        if (noteType.equals("edit")) {
            var noteTitle = intent.getStringExtra("noteTitle")
            var noteDesc = intent.getStringExtra("noteDesc")
            var noteDate = intent.getStringExtra("noteDate")
            var noteId = intent.getIntExtra("noteId", noteIdpassed)
            binding.addNote.setText(noteDesc)
            binding.addTitle.setText(noteTitle)
        }

        binding.imgTickbtn.setOnClickListener {


            ///this is used when user wants to update an note
            if (noteType.equals("edit")) {
                var updatedTitle = binding.addTitle.text.toString()
                var updatedDesc = binding.addNote.text.toString()
                if (updatedTitle.isNotEmpty() && updatedDesc.isNotEmpty()) {
                    var sdf = SimpleDateFormat("EEE d MM YYYY HH:MM a")
                    var currentdataOrtime: String = sdf.format(Date())
                    var updatedNote = Note(noteIdpassed, updatedTitle, updatedDesc, currentdataOrtime)

                    viewModel.update(updatedNote)
                    backToMainActivity()
                    Toast.makeText(this@AddNote, "Note Updated..", Toast.LENGTH_LONG).show()

                }
            }
            var newtitle = binding.addTitle.text.toString()
            var newnote = binding.addNote.text.toString()
            //this is used when user wants to create an New Note
            if (noteType.equals("makeNewnote")) {

                var sdf = SimpleDateFormat("EEE d/MM/YYYY HH::MM a")
                var currentDateOrTime = sdf.format(Date())
                var newNote = Note(null, newtitle, newnote, currentDateOrTime)
                viewModel.insertNote(newNote)
                backToMainActivity()
                Toast.makeText(this@AddNote, "New Note Added", Toast.LENGTH_LONG).show()

            }

        }


        binding.imgBackbtn.setOnClickListener {
            onBackPressed()
        }

    }


    private fun backToMainActivity() {
        var intent = Intent(this@AddNote, MainActivity::class.java)
        startActivity(intent)
    }
}

