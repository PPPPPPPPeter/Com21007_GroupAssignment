package com.example.groupassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener

import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar:Toolbar = findViewById(R.id.toolbarMain)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        toolbar.setNavigationOnClickListener(object : OnClickListener{
            override fun onClick(v: View?) {
                println("===== Click ! =====")
                drawerLayout.openDrawer(GravityCompat.START)
            }

        })


    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }



}