package com.saddam.challenge4binar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//ROOM DATABASE for Catatan
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    //function to call DAO interface, DAO interface must have been defined
    abstract fun noteDao() : NoteDao

    //function to get and destroy instance of catatan database
    companion object{
        private var INSTANCE : NoteDatabase? = null
        fun getInstance(context : Context):NoteDatabase? {
            if (INSTANCE == null){
                synchronized(NoteDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NoteDatabase::class.java,"Note.db").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}