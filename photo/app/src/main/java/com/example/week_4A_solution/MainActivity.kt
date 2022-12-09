package com.example.week_4A_solution

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>
//    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private val myDataset: MutableList<ImageElement> = ArrayList<ImageElement>()

    val photoPicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            val element = ImageElement(uri)
            myDataset.add(element)
            mRecyclerView.scrollToPosition(myDataset.size - 1)
        }
    }

    var pickFromCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){uri->
        val photo_uri = uri.data?.extras?.getString("uri")
        if (photo_uri!=null){
            val element = ImageElement(Uri.parse(photo_uri))
            myDataset.add(element)
            mRecyclerView.scrollToPosition(myDataset.size - 1)
            //kun daoxiamiuan
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        mRecyclerView = findViewById<RecyclerView>(R.id.my_list)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        val numberOfColumns = 4
        mRecyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)

        // specify an adapter (see also next example)
        mAdapter = MyAdapter(this, myDataset) as RecyclerView.Adapter<RecyclerView.ViewHolder>
        mRecyclerView.adapter = mAdapter



        val photoPickerFab: FloatingActionButton = findViewById<FloatingActionButton>(R.id.openGalleryFab)
        photoPickerFab.setOnClickListener(View.OnClickListener { view ->
            photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        })

        val cameraPickerFab: FloatingActionButton = findViewById<FloatingActionButton>(R.id.openCamFab)
        cameraPickerFab.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(this, CameraActivity::class.java)
            pickFromCamera.launch(intent)
        })
    }

    private fun initData() {
//        repeat(4){
//            myDataset.add(ImageElement(R.drawable.joe1))
//            myDataset.add(ImageElement(R.drawable.joe2))
//            myDataset.add(ImageElement(R.drawable.joe3))
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}