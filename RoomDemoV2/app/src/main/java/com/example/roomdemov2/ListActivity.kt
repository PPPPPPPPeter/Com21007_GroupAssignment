package com.example.roomdemov2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)
        val floatingBtn : FloatingActionButton = findViewById(R.id.floatingActionButton)

        floatingBtn.setOnClickListener(object : OnClickListener{
            override fun onClick(v: View?) {
               click()
            }

        })

    }

    private fun click() {
        val toAddActivity : Intent = Intent(this, AddActivity::class.java)
        startActivity(toAddActivity)
    }
}