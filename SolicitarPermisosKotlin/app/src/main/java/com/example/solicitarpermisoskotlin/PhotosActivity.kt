package com.example.solicitarpermisoskotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solicitarpermisoskotlin.adapter.PhotosAdapter
import com.example.solicitarpermisoskotlin.adapter.UsersAdapter
import com.example.solicitarpermisoskotlin.retrofit.RetrofitInstance
import com.example.solicitarpermisoskotlin.retrofit.RetrofitServices
import com.example.solicitarpermisoskotlin.retrofit.model.PhotosResponse
import com.example.solicitarpermisoskotlin.retrofit.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosActivity : AppCompatActivity() {

    private lateinit var recycler : RecyclerView
    private lateinit var listaPhotos: ArrayList<PhotosResponse>
    private lateinit var photosAdapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        initialize()
        photossList()
    }

    private fun  initialize() {
        recycler = findViewById(R.id.recyclerFotos)
        initializeRecyclerView()

    }

    private fun  initializeRecyclerView() {
        val manager = LinearLayoutManager(this)
        listaPhotos = ArrayList()
        photosAdapter = PhotosAdapter(listaPhotos)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = manager
        recycler.adapter = photosAdapter

        val dividerItemDecoration = DividerItemDecoration(recycler.context, manager.orientation)
        recycler.addItemDecoration(dividerItemDecoration)
    }

    private fun photossList() {
        val retrofitServices = RetrofitInstance.getInstance()?.create(RetrofitServices::class.java)
        val responseCall = retrofitServices?.getPhotos()

        responseCall?.enqueue(object: Callback<List<PhotosResponse>> {
            override fun onResponse(call: Call<List<PhotosResponse>>, response: Response<List<PhotosResponse>>) {
                if(response.body() != null) {
                    val photosResponse = response.body() as ArrayList<PhotosResponse>
                    updateRecycler(photosResponse)
                }
            }

            override fun onFailure(call: Call<List<PhotosResponse>>, t: Throwable) {
                println(t)
            }
        })
    }

    private fun updateRecycler(updatePhotos: ArrayList<PhotosResponse>) {
        if(updatePhotos.size > 0) {
            listaPhotos.clear()
            listaPhotos.addAll(updatePhotos)
            photosAdapter.notifyDataSetChanged()
        }
    }

    fun regresar(view: View) {
        val intent = Intent(this, PhotoActivity::class.java).apply {
        }
        startActivity(intent)
    }
}