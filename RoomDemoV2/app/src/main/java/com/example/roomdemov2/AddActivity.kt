package com.example.roomdemov2

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.roomdemov2.model.User
import com.example.roomdemov2.viewModel.UserViewModel

class AddActivity : AppCompatActivity(){

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val btnAddActivity : Button = findViewById(R.id.button2)
        btnAddActivity.setOnClickListener(object : OnClickListener{
            override fun onClick(v: View?) {
               click()
            }
        })
    }

    private fun click() {
        insertToDatabase()
    }


    // INSERT To Database:
    private fun insertToDatabase() {

        val textFirstName : EditText = findViewById(R.id.editTextTextPersonName)
        val textLastName : EditText = findViewById(R.id.editTextTextPersonName2)
        val textAge : EditText = findViewById(R.id.editTextTextPersonName3)

        val firstName : String = textFirstName.text.toString()
        val lastName : String = textLastName.text.toString()
        val age  = textAge.text

        println("==="+firstName+"==="+lastName+"==="+age)

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || age.isEmpty()){
            println("================== Please fill the different lines !==================")
            return
        }
        else{
            var user:User = User(0, firstName, lastName,
                Integer.parseInt(age.toString()))

            mUserViewModel.addUser(user)
            println("Successfully added")
            Thread.sleep(100)

        }

    }

}