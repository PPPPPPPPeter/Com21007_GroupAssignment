package com.example.trackingsensor

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*

class LocalService : Service() {
    private val binder = LocationInfoBinder()
    var mMapViewModel: MapViewModel? = null
    private var workThread: Thread? = null
    private var timeThread: Thread? = null
    var isStart = false
    var format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    fun start() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        isStart = true
        workThread = object : Thread() {
            @SuppressLint("MissingPermission")
            override fun run() {
                super.run()
                try {
                    while (!isInterrupted && isStart) {
                        val locationResult = fusedLocationProviderClient.lastLocation
                        locationResult.addOnCompleteListener {
                            mMapViewModel?.uploadLocation(it.result)
                        }
                        sleep(20 * 1000)
                    }
                }catch (e:Exception){

                }

            }
        }
        workThread?.start()
        timeThread =object :Thread(){
            override fun run() {
                super.run()
                try {
                    while (!isInterrupted && isStart) {
                        mMapViewModel?.time?.postValue(format.format(Date()))
                        sleep( 1000)
                    }
                }catch (e:Exception){

                }

            }
        }
        timeThread?.start()
    }

    fun stop() {
        isStart = false
        if (workThread != null) {
            workThread!!.interrupt()
            workThread = null
        }
        if (timeThread != null) {
            timeThread!!.interrupt()
            timeThread = null
        }
    }

    inner class LocationInfoBinder : Binder() {
        fun getService(): LocalService {
            return this@LocalService;
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}