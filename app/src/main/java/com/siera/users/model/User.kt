package com.siera.users.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User(val name: String?, val number: String?) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}