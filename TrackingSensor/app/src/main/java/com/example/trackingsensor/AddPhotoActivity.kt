package com.example.trackingsensor

import android.R
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.trackingsensor.databinding.ActivityAddBinding
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream


class AddPhotoActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

    var uri: Uri? = null
    var path: String? = null
    var mBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toobar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSelectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).also {
                it.addCategory(Intent.CATEGORY_OPENABLE)
                it.type = "image/*"
                it.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            mLauncherAlbum.launch(intent)
          //  mLauncherAlbum.launch(intent)

        }
        binding.btnTakePhoto.setOnClickListener { mLauncherCamera.launch(null) }
        binding.btnUpload.setOnClickListener {
            var title = binding.edTitle.text.toString()
            var describe = binding.edDescribe.text.toString()
            if (title.isNullOrEmpty()) {
                Toast.makeText(this, "Please Input Title", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (describe.isNullOrEmpty()) {
                Toast.makeText(this, "Please Input Describe", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (uri == null) {
                Toast.makeText(this, "Please Add Photo", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            setResult(RESULT_OK, Intent().apply {
                putExtra("title", title)
                putExtra("describe", describe)
                putExtra("uri", uri)
                putExtra("path", path)
            })
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //拍照
    private val mLauncherCamera = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { result: Bitmap? ->
        if (result != null) {
           uri= Uri.parse(
                MediaStore.Images.Media.insertImage(
                    contentResolver,
                    result,
                    System.currentTimeMillis().toString(),
                    null
                )
            )
            path = uri.toString()
            binding.ivPhoto.setImageURI(uri)
        }

    }

    //选取图片
    private val mLauncherAlbum = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode== RESULT_OK) {
            uri =    result.data?.data;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                contentResolver.takePersistableUriPermission(
                    uri!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
            path = uri.toString()
            binding.ivPhoto.setImageURI(uri)

        }

    }


    /**
     * 获取真实的bitmap，返回的是处理过rotate的bitmap
     * @param resolver
     * @param uri
     * @return
     */
    fun decodeImageLegacy(resolver: ContentResolver, uri: Uri?): Bitmap? {
        var bitmap: Bitmap? = null
        var inputStream: InputStream? = null
        try {
            inputStream = resolver.openInputStream(uri!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return bitmap
    }
}