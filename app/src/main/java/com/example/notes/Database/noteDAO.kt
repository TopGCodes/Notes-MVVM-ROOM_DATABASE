package com.example.notes.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.Models.Note


@Dao
interface noteDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note : Note)

    @Delete
    suspend fun  delete(note : Note)

    @Query("SELECT * FROM Notes_table order by id ASC")
     fun  getAllNotes() : LiveData<List<Note>>

    @Query("UPDATE Notes_table Set title = :title, Note = :Note WHERE id = :id")
    suspend fun  update(id : Int?, title : String?, Note : String?)


}