package com.benny.uploadimage.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.benny.uploadimage.databinding.ActivityMainBinding
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var filePath: Uri
    lateinit var bitmap : Bitmap
    lateinit var decoded : Bitmap
    val PICK_IMAGE_REQUEST = 1
    val bitmap_size = 60
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambahImage.setOnClickListener{
            showFileChooser()
        }
    }

    private fun showFileChooser() {
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK){
                val data: Intent? = result.data
                filePath = data?.data!!
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                    setToImageView(getResizedBitmap(bitmap, 512)!!)
                }catch (e: IOException){
                    Log.d("Error::", e.toString())
                }

            }
        }
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    // fungsi resize image
    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    private fun setToImageView(bmp: Bitmap) {
        //compress image
        val bytes = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes)
        decoded = BitmapFactory.decodeStream(ByteArrayInputStream(bytes.toByteArray()))

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        binding.imgUpload.setImageBitmap(decoded)
    }


}