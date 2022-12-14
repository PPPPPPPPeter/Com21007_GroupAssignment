package com.example.trackingsensor

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.trackingsensor.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val ACTION_REQUEST_PERMISSIONS = 101
    private val NEEDED_PERMISSIONS = arrayListOf<String>(
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    var mStorageLancher  =registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode!= Activity.RESULT_OK){
            finish()
        }
    }
    private val TAG ="jcy-MapsActivity"

    private var polyline: Polyline?=null;
    private var mMap: GoogleMap?=null;
    private lateinit var binding: ActivityMapsBinding
    private  var mLocalService:LocalService?=null
    private lateinit var viewModel :MapViewModel
    private var serviceCon  = object:ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            service?.let {
                mLocalService= (service as LocalService.LocationInfoBinder).getService();
                mLocalService?.mMapViewModel=viewModel
            }

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mLocalService=null;
        }

    }
    private fun isExternalStorageManager(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 没文件管理权限时申请权限
            if (!Environment.isExternalStorageManager()) {
                var intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                mStorageLancher.launch(intent)
                return
            }
        }


    }
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(
                this,
                NEEDED_PERMISSIONS.toTypedArray(),
                ACTION_REQUEST_PERMISSIONS
            )
        }else{
            isExternalStorageManager()
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        var serviceIntent = Intent(this,LocalService::class.java)
        bindService(serviceIntent,serviceCon, BIND_AUTO_CREATE)
        binding.btnStart.setOnClickListener {
            if(mLocalService?.isStart==true){
                binding.btnStart.setText("Start")
                mLocalService?.stop()
                binding.llWeather.visibility= View.GONE
                binding.btnAdd.visibility= View.GONE
            }else{
                binding.llWeather.visibility= View.VISIBLE
                binding.btnAdd.visibility= View.VISIBLE
                mLocalService?.start()
                binding.btnStart.setText("Stop")
            }
        }
        viewModel.mLocation.observe(this){
            it?.let { loc->
                mMap?.isMyLocationEnabled = true
                mMap?.uiSettings?.isMyLocationButtonEnabled = true
                mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(loc.latitude,loc.longitude),17f))
            }
        }
        viewModel.weather.observe(this){
            binding.tvWeather.setText(it)
        }
        viewModel.temp.observe(this){
            binding.tvTmp.setText("temp ：$it")
        }
        viewModel.pressure.observe(this){
            binding.tvPressure.setText("Pressure: $it")
        }
        viewModel.time.observe(this){
            binding.tvTime.setText(it)
        }
        viewModel.isUpdatePath.observe(this){
            if(viewModel.mLocationPath.isNotEmpty()){
                if(polyline==null){
                    var  polylineOptions = PolylineOptions()
                    polylineOptions.addAll(viewModel.mLocationPath)
                    polyline = mMap?.addPolyline(polylineOptions)
                }else{
                    polyline?.points =viewModel.mLocationPath
                }

            }

        }
        binding.btnAdd.setOnClickListener {
            if(viewModel.mLocation.value==null){
                Toast.makeText(this, "Get Location Failed", Toast.LENGTH_LONG).show()
            }else{
                mLauncherAddPhoto.launch(Intent(this,AddPhotoActivity::class.java))
            }

        }
    }

    private val mLauncherAddPhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if(result.resultCode== RESULT_OK){
            viewModel.mLocation.value?.let {
                val sydney = LatLng(it.latitude, it.longitude)
                var title =result.data?.getStringExtra("title")
                var path =result.data?.getStringExtra("path")
                var describe =result.data?.getStringExtra("describe")
                var uri =result.data?.getParcelableExtra<Uri>("uri")
                mMap?.addMarker(MarkerOptions().position(sydney).title(title))?.let {
                    viewModel.mMarkerList.add(it)
                    viewModel.mPhotoList.add(LocalPhoto(uri,path,sydney,title!!,describe!!,it.id))
                }

            }
        }


    }

    override fun onResume() {
        super.onResume()
        if(mLocalService?.isStart==true){
            binding.btnStart.setText("Stop")
            binding.llWeather.visibility= View.VISIBLE
            binding.btnAdd.visibility= View.VISIBLE
        }else{
            binding.btnStart.setText("Start")
            binding.llWeather.visibility= View.GONE
            binding.btnAdd.visibility= View.GONE

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceCon)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.setOnMarkerClickListener {marker->
            viewModel.mPhotoList.forEach {
                if(it.markId.equals(marker.id)){
                    startActivity(Intent(this,PhotoDetailActivity::class.java).apply {
                        putExtra("title",it.title)
                        putExtra("describe",it.desc)
                        putExtra("uri",it.uri)
                        putExtra("path",it.path)
                        putExtra("Location",it.location)
                    })
                }
            }
            false
        }
        // Add a marker in Sydney and move the camera
      /*  val sydney = LatLng(-34.0, 151.0)
        mMap?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
    }

    /**
     * 请求权限的回调
     *
     * @param requestCode  请求码
     * @param isAllGranted 是否全部被同意
     */
    private fun afterRequestPermission(requestCode: Int, isAllGranted: Boolean) {
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            if (!isAllGranted) {
                finish()
            }else{
                isExternalStorageManager()
            }
        }
    }

    /**
     * 权限检查
     *
     * @param neededPermissions 需要的权限
     * @return 是否全部被允许
     */
    protected fun checkPermissions(neededPermissions: List<String>?): Boolean {
        if (neededPermissions == null || neededPermissions.size == 0) {
            return true
        }
        var allGranted = true
        for (neededPermission in neededPermissions) {
            Log.d(
                TAG,
                "checkPermissions: neededPermission " + neededPermission + " check " + ContextCompat.checkSelfPermission(
                    this,
                    neededPermission
                )
            )
            allGranted = allGranted && ContextCompat.checkSelfPermission(
                this,
                neededPermission
            ) == PackageManager.PERMISSION_GRANTED
        }
        return allGranted
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var isAllGranted = true
        for (grantResult in grantResults) {
            isAllGranted = isAllGranted and (grantResult == PackageManager.PERMISSION_GRANTED)
        }
        afterRequestPermission(requestCode, isAllGranted)
    }
}