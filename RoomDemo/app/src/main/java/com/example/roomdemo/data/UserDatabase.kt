package com.example.roomdemo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdemo.model.User

// As 正式的database
// entities也就是Use类
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao():UserDao

    companion object{

        @Volatile
        private var INSTANCE : UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase{
            //将INSTANCE赋给temInstance：
            val temInstance = INSTANCE

            //1.--------检查INSTANCE如果是否为null，非null返回temInstance：
            if (temInstance != null){
                return temInstance
            }

            //2.--------如果INSTANCE为null，则创建一个新的INSTANCE：
            // synchronized block 提供线程保护：
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java, "user_database"
                ).build()
                INSTANCE = instance
                return instance

            }
        }

    }

}

