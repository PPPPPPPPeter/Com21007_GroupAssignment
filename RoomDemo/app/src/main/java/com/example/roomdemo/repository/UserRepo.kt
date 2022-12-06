package com.example.roomdemo.repository

import androidx.lifecycle.LiveData
import com.example.roomdemo.data.UserDao
import com.example.roomdemo.model.User

class UserRepo(private val userDao: UserDao){

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser()
    }

}