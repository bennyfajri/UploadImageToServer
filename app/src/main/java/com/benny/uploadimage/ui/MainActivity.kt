package com.benny.uploadimage.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.benny.uploadimage.databinding.ActivityMainBinding
import com.benny.uploadimage.model.UploadModel
import com.benny.uploadimage.repository.Repository
import com.benny.uploadimage.viewmodel.ViewModels
import com.benny.uploadimage.viewmodel.ViewModelsFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var bitmap : Bitmap
    lateinit var decoded : Bitmap
    lateinit var image: String
    lateinit var name: String
    lateinit var viewModel: ViewModels
    val PICK_IMAGE_REQUEST = 1
    val bitmap_size = 60
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = ViewModelsFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewModels::class.java)


        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                if(Build.VERSION.SDK_INT < 28 ){
                    bitmap = MediaStore.Images.Media.getBitmap(
                        this.contentResolver, it
                    )
                    setToImageView(getResizedBitmap(bitmap, 512)!!)
                    showExtraContent()
                }else {
                    val source = ImageDecoder.createSource(this.contentResolver, it)
                    bitmap = ImageDecoder.decodeBitmap(source)
                    setToImageView(getResizedBitmap(bitmap, 512)!!)
                    showExtraContent()
                }
            }
        )

        binding.btnTambahImage.setOnClickListener{
            getImage.launch("image/*")
        }

        binding.btnUpload.setOnClickListener {
            getStringImage(decoded)
//            Log.d("result:::", image)
            name = binding.etNama.text.toString()
            if(name.isEmpty() || binding.imgUpload.drawable == null){
                Toast.makeText(
                    applicationContext,
                    "isi nama atau upload photo dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val request = UploadModel(name, image)
                viewModel.insertImage(request)
                viewModel.myResponse.observe(this, Observer { response ->
                    if(response.isSuccessful){
                        response.body()?.let { it1 -> Log.d("Response::", it1.message) }
                    }
                })
            }
        }
    }

    private fun getStringImage(bmp: Bitmap) {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos)
        val imageBytes = baos.toByteArray()
        image = Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    private fun showExtraContent() {
        binding.btnUpload.visibility = View.VISIBLE
        binding.etNama.visibility = View.VISIBLE
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