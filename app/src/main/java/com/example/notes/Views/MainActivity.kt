package com.example.notes.Views

import android.app.Activity
import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.Adapter.notesAdapter
import com.example.notes.Database.NoteDatabase
import com.example.notes.Models.Note
import com.example.notes.Models.NoteViewmodel
import com.example.notes.R
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), notesAdapter.Noteitemclicklistener, notesAdapter.OnDeleteclicklistener
   {
    private lateinit var binding : ActivityMainBinding

    //Using Variables
    private lateinit var viewModel: NoteViewmodel
    private lateinit var database: NoteDatabase
    private lateinit var adapter: notesAdapter





    //ONCREate MEthod
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()




        init()
        //initialize the UI or ViewModel
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewmodel::class.java)


        viewModel.allnotes.observe(this) { list ->
            list?.let {
                adapter.updatelist(list)
            }
        }


        database = NoteDatabase.getDatabase(this)
    }

    ////////////////////////////////////////////////////////////////////////
    private fun init() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = notesAdapter(this, this,this)
        binding.recyclerView.adapter = adapter

        binding.floatingActionButton.setOnClickListener {

            val intent = Intent(this@MainActivity, AddNote::class.java)
            intent.putExtra("noteType","makeNewnote")
            startActivity(intent)

        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filterlist(newText)
                    return true
                }
                return false
            }

        })
    }

    override fun onItemClick(note: Note) {

        val intent = Intent(this@MainActivity, AddNote::class.java)
        intent.putExtra("noteType", "edit")
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteDesc", note.Note)
        intent.putExtra("noteDate", note.date)
        intent.putExtra("noteId", note.id)
        startActivity(intent)

    }

    override fun onDeleteClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this@MainActivity, "Your Note is Deleted", Toast.LENGTH_LONG).show()
    }


    /////////////////////////////////////////////////////////////////////
}


