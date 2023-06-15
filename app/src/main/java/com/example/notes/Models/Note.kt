package com.example.notes.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Creating an table for database what are the columns should have in table
@Entity(tableName = "Notes_table")
data class Note (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")var id : Int?,
    @ColumnInfo("title")var title : String,
    @ColumnInfo(name="Note")var Note : String,
    @ColumnInfo(name = "date") var date : String
        )
