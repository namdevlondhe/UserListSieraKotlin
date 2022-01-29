package com.siera.users.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.siera.users.model.User
import com.siera.users.room.UserDao
import com.siera.users.room.UserDatabase

class UserRepository(application: Application) {
    private val userDao: UserDao?
    val allUsers: LiveData<List<User?>?>?
    fun insert(user: User?) {
        InsertUserAsyncTask(userDao).execute(user)
    }

    fun update(user: User?) {
        UpdateUserAsyncTask(userDao).execute(user)
    }

    fun delete(user: User?) {
        DeleteUserAsyncTask(userDao).execute(user)
    }

    fun deleteAllUsers() {
        DeleteAllUsersAsyncTask(userDao).execute()
    }

    private class InsertUserAsyncTask(private val userDao: UserDao?) :
        AsyncTask<User?, Void?, Void?>() {
        protected override fun doInBackground(vararg users: User?): Void? {
            userDao!!.insertUser(users[0])
            return null
        }
    }

    private class UpdateUserAsyncTask(private val userDao: UserDao?) :
        AsyncTask<User?, Void?, Void?>() {
        protected override fun doInBackground(vararg users: User?): Void? {
            userDao!!.updateUser(users[0])
            return null
        }
    }

    private class DeleteUserAsyncTask(private val userDao: UserDao?) :
        AsyncTask<User?, Void?, Void?>() {
        protected override fun doInBackground(vararg users: User?): Void? {
            userDao!!.deleteUser(users[0])
            return null
        }
    }

    private class DeleteAllUsersAsyncTask(private val userDao: UserDao?) :
        AsyncTask<Void?, Void?, Void?>() {
        protected override fun doInBackground(vararg voids: Void?): Void? {
            userDao!!.deleteAllUsers()
            return null
        }
    }

    init {
        val database: UserDatabase = UserDatabase.Companion.getInstance(application)!!
        userDao = database.userDao()
        allUsers = userDao.allUsers
    }
}