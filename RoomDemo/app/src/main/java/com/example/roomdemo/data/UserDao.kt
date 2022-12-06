package com.example.roomdemo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomdemo.model.User

// As controller of database:
// As User Database Access Object:

@Dao
interface UserDao {

    // 需要自动忽略一波内部完全相同的User object:
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    // 后面困难要使用一波coroutines, 所以使用suspend：
    //这个函数： 插入：
    suspend fun addUser()

    // 写一个函数读取database中的table并返回一个list，ASC指从小到大排列（升序）
    // 这个函数：读取：
    @Query("SELECT * FROM User_Table_For_testing ORDER BY id ASC")

    fun readAllData(): LiveData<List<User>>

}