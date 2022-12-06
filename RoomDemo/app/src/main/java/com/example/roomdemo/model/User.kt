package com.example.roomdemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// As Entity of the database:
// create a table :

@Entity(tableName = "User_Table_For_testing")
data class User(
    // Pkey 默认为true
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val firstName : String,
    val lastName : String,
    val age : Int

)