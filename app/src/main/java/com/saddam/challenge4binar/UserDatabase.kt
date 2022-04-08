package com.saddam.challenge4binar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Database for user
@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    //function to call user DAO interface, userDAO must have been defined
    abstract fun userDaoInterface() : UserDao

    //function to get and destroy instance of user database
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