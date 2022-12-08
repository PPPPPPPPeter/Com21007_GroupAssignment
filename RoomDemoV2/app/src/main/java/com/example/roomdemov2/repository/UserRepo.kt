package com.example.roomdemov2.repository

import androidx.lifecycle.LiveData
import com.example.roomdemov2.data.UserDao
import com.example.roomdemov2.model.User

class UserRepo(private val userDao: UserDao){

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

}