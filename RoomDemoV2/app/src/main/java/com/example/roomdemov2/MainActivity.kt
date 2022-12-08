package com.example.roomdemov2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMainAct : Button = findViewById(R.id.button3)

        btnMainAct.setOnClickListener(object : OnClickListener{
            override fun onClick(v: View?) {
               click()
            }
        })

    }

    private fun click() {
        var toActList :Intent = Intent(this, ListActivity::class.java)
        startActivity(toActList)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}