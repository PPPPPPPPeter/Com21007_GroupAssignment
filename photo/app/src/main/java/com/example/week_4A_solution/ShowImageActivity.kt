package com.example.week_4A_solution

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.week_4A_solution.MyAdapter.Companion.items
import com.example.week_4A_solution.databinding.ActivityShowImageBinding

class ShowImageActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityShowImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)

        // intent is a property of the activity. intent.extras returns any data that was pass
        // along with the intent.
        val bundle: Bundle? = intent.extras
        var position = -1

        if (bundle!= null) {
            // this is the image position in the items List
            position = bundle.getInt("position")
            if (position != -1) {
                val imageView = findViewById<ImageView>(R.id.image)
                val element = MyAdapter.items[position]

                element.image?.let {
                    imageView.setImageResource(it)
                }
                element.file_uri?.let {
                    imageView.setImageURI(it)
                }
            }
        }
    }
}