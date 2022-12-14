package com.example.trackingsensor

import android.graphics.Bitmap
import android.net.Uri
import com.google.android.gms.maps.model.LatLng

data class LocalPhoto(val uri: Uri?,  val path:String?, val location:LatLng, val title:String, val desc:String, val markId:String)