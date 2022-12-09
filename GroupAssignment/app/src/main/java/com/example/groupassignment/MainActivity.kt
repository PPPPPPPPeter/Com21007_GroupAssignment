package com.example.groupassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)






    }

    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_main)
        val toolbar:Toolbar = findViewById(R.id.toolbarMain)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val floatingBtnStart : FloatingActionButton = findViewById(R.id.floatingActionBtnStart)
        val floatingBtnStop : FloatingActionButton = findViewById(R.id.floatingActionBtnStop)

        toolbar.setNavigationOnClickListener(object : OnClickListener{
            override fun onClick(v: View?) {
                println("<<<<<< Navigation Side Clicked >>>>>>")
                drawerLayout.openDrawer(GravityCompat.START)
            }

        })

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


    private fun showFill_TitleWin(){
//        val winTitle : AlertDialog.Builder = AlertDialog.Builder(this)
//        winTitle.setTitle("New Visit!")
//        winTitle.

    }



}