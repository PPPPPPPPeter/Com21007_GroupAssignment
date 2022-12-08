package com.example.roomdemov2.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

import com.example.roomdemov2.data.UserDatabase
import com.example.roomdemov2.model.User
import com.example.roomdemov2.repository.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel(application: Application): AndroidViewModel(application){

    private val readAllData: LiveData<List<User>>
    private val repository: UserRepo

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepo(userDao)
        readAllData = repository.readAllData

    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }
}