package com.example.solicitarpermisoskotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.solicitarpermisoskotlin.retrofit.RetrofitInstance
import com.example.solicitarpermisoskotlin.retrofit.RetrofitServices
import com.example.solicitarpermisoskotlin.retrofit.model.PhotosResponse
import com.example.solicitarpermisoskotlin.retrofit.model.UserResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoActivity : AppCompatActivity() {

    private lateinit var edtBuscarFoto : EditText
    private lateinit var btnBuscarFoto : Button
    private lateinit var btnMostrarFotos : Button
    private lateinit var imgPhoto : ImageView
    private lateinit var txtAlbumId : TextView
    private lateinit var txtIdPhoto : TextView
    private lateinit var txtTitle : TextView
    private lateinit var txtUrl : TextView
    private lateinit var txtThumbnailUrl : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)


        initialize()
    }

    private fun  initialize() {
        edtBuscarFoto = findViewById(R.id.edtBuscarFoto)
        btnBuscarFoto = findViewById(R.id.btnBuscarFoto)
        btnMostrarFotos = findViewById(R.id.btnMostrarFotos)
        imgPhoto = findViewById(R.id.imgPhoto)
        txtAlbumId = findViewById(R.id.txtAlbumId)
        txtIdPhoto = findViewById(R.id.txtIdPhoto)
        txtTitle = findViewById(R.id.txtTitlePhoto)
        txtUrl = findViewById(R.id.txtUrl)
        txtThumbnailUrl = findViewById(R.id.txtThumbnail)
        setContent(PhotosResponse())
    }

    fun getPhotos(view: View) {
        btnBuscarFoto.isEnabled = false
        setContent(PhotosResponse())
        photo()
    }

    fun viewList(view: View) {
        val intent = Intent(this, PhotosActivity::class.java).apply {
        }
        startActivity(intent)
    }

    private fun photo() {
        val retrofitServices = RetrofitInstance.getInstance()?.create(RetrofitServices::class.java)
        val responseCall = retrofitServices?.getPhoto(edtBuscarFoto.text.toString())

        responseCall?.enqueue(object: Callback<PhotosResponse> {
            override fun onResponse(call: Call<PhotosResponse>, response: Response<PhotosResponse>) {
                if(response.isSuccessful && response.body() != null) {
                    val photoResponse = response.body() as PhotosResponse
                    updateUI(photoResponse)
                    // response.body() as ArrayList<MessageResponse>
                    // var messageResponse : MessageResponse()
                }
                btnBuscarFoto.isEnabled = true
            }

            override fun onFailure(call: Call<PhotosResponse>, t: Throwable) {
                println(t)
                btnBuscarFoto.isEnabled = true
            }
        })
    }

    private fun updateUI(photosResponse: PhotosResponse) {
        if(photosResponse != null) {
            setContent(photosResponse)
            Picasso
                .get()
                .load(photosResponse.url)
                .into(imgPhoto)
        }
    }

    private fun setContent(photosResponse: PhotosResponse) {
        if(photosResponse != null) {
            txtAlbumId.text = photosResponse.albumId.toString()
            txtIdPhoto.text = photosResponse.id.toString()
            txtTitle.text = photosResponse.title
            txtUrl.text = photosResponse.url
            txtThumbnailUrl.text = photosResponse.thumbnailUrl
            //url   https://via.placeholder.com/600/24f355
           
        } else {
            txtAlbumId.text = ""
            txtIdPhoto.text = ""
            txtTitle.text = ""
            txtUrl.text = ""
            txtThumbnailUrl.text = ""
        }
    }

}