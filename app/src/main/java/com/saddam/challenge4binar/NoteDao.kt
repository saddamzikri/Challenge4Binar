package com.saddam.challenge4binar

import androidx.room.*
//DAO interface
@Dao
interface NoteDao{
    @Insert fun insertNote(note: Note) : Long //membuat data baru
    @Query("SELECT * FROM Note") fun getAllNote() : List<Note> //view data
    @Delete fun deleteDataNote(note: Note) : Int //delete data
    @Update fun updateDataNote(note: Note) : Int //edit data
}