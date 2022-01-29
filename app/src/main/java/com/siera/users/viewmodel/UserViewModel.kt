package com.siera.users.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.siera.users.model.User
import com.siera.users.repository.UserRepository

public class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository(application)
    val allUsers: LiveData<List<User?>?>? = repository.allUsers
    fun insert(user: User?) {
        repository.insert(user)
    }

    fun update(user: User?) {
        repository.update(user)
    }

    fun delete(user: User?) {
        repository.delete(user)
    }

    fun deleteAllUsers() {
        repository.deleteAllUsers()
    }

}