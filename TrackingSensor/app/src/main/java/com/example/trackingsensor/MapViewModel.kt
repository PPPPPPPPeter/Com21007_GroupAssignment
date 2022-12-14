package com.example.trackingsensor

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException
import java.text.SimpleDateFormat


class MapViewModel:ViewModel() {
    var mLocationPath:MutableList<LatLng> = arrayListOf()
    var isUpdatePath =  MutableLiveData<Boolean>(false)
    var mLocation= MutableLiveData<Location?>(null)
    val httpClient = OkHttpClient().newBuilder().build();
    private var gson = Gson();
    private val TAG = "jcy-MapViewModel"
    var temp = MutableLiveData<String>("None")
    var pressure = MutableLiveData<String>("None")
    var weather = MutableLiveData<String>("None")
    var time = MutableLiveData<String>("None")
    var mPhotoList = arrayListOf<LocalPhoto>()
    var mMarkerList = arrayListOf<Marker>()


    //间隔20秒更新一次数据
    fun uploadLocation(loc:Location){
        val reqBuild: Request.Builder = Request.Builder()
        val urlBuilder =
            "https://api.openweathermap.org/data/2.5/weather".toHttpUrlOrNull()?.newBuilder() ?:null

        urlBuilder?.addQueryParameter("lat", loc.latitude.toString() )
        urlBuilder?.addQueryParameter("lon", loc.longitude.toString() )
        urlBuilder?.addQueryParameter("appid", "4ab19b9427a56ff8e9f86ccd14a477a5" )
        urlBuilder?.addQueryParameter("units", "metric" )
        Log.d(TAG, "uploadLocation: ")
        mLocation.postValue(loc)
        var last = mLocationPath.lastOrNull()
        if(last==null){
            mLocationPath.add(LatLng(loc.latitude,loc.longitude))
            isUpdatePath.postValue(!isUpdatePath.value!!)
        }else if(last.latitude!=loc.latitude||last.longitude!=loc.longitude){
            mLocationPath.add(LatLng(loc.latitude,loc.longitude))
            isUpdatePath.postValue(!isUpdatePath.value!!)
        }
        urlBuilder?.let {
            reqBuild.url(urlBuilder.build())
            val request = reqBuild.build()
            httpClient.newCall(request).enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful){
                        var weatherJson = response.body?.string();
                        weatherJson?.let {
                            var w  = gson.fromJson<Weather>(weatherJson,Weather::class.java)
                            if(w!=null){
                              //  Log.d(TAG, "onResponse: $w")
                                temp.postValue("${w.main?.temp}℃")
                                pressure.postValue("${w.main?.pressure}hPa")
                                var weatherDetail =  w.weather?.firstOrNull();
                                weatherDetail?.let {
                                    weather.postValue(weatherDetail?.description)
                                }

                            }
                        }

                    }
                }

            })
        }

    }
}