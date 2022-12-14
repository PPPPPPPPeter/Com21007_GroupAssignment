package com.example.trackingsensor

import android.R
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.trackingsensor.databinding.ActivityAddBinding
import com.example.trackingsensor.databinding.ActivityDetailBinding
import com.google.android.gms.maps.model.LatLng

class PhotoDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toobar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var path = intent.getStringExtra("path")
        var uri = intent.getParcelableExtra<Uri>("uri")
        var location = intent.getParcelableExtra<LatLng>("Location")
        var tite =  intent.getStringExtra("tite")
        var describe =  intent.getStringExtra("describe")
        binding.ivPhoto.setImageURI(Uri.parse(path))
        binding.tvDescribe.setText("Describe : $describe")
        binding.tvTitle.setText("Tite : $tite")
        binding.tvLoc.setText("Location : ${location?.latitude},${location?.longitude}")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}