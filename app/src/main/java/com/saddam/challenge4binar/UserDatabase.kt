package com.saddam.challenge4binar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Database untuk user
@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    //function untuk memanggil user DAO interface, userDAO harus telah di defined
    abstract fun userDaoInterface() : UserDao

    //function untuk get dan destroy instance dari user database
    companion object{
        private var INSTANCE : UserDatabase? = null
        fun getInstance(context : Context):UserDatabase? {
            if (INSTANCE == null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserDatabase::class.java,"User.db").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}