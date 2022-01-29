package com.siera.users.room

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.siera.users.model.User
import com.siera.users.room.UserDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    private class PopulateDbAsyncTask(db: UserDatabase?) : AsyncTask<Void?, Void?, Void?>() {
        private val userDao: UserDao
        protected override fun doInBackground(vararg voids: Void?): Void? {
            return null
        }

        init {
            userDao = db!!.userDao()
        }
    }

    companion object {
        private var instance: UserDatabase? = null
        @Synchronized
        fun getInstance(context: Context): UserDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java, "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance
        }

        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }
}