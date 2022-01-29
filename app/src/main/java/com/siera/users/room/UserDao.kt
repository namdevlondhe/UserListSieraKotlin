package com.siera.users.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.siera.users.model.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User?)

    @Update
    fun updateUser(user: User?)

    @Delete
    fun deleteUser(user: User?)

    @Query("DELETE FROM user_table")
    fun deleteAllUsers()

    @get:Query("SELECT * FROM user_table")
    val allUsers: LiveData<List<User?>?>?
}