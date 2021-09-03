package com.example.solicitarpermisoskotlin

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solicitarpermisoskotlin.adapter.AdaptadorRecycler
import com.example.solicitarpermisoskotlin.retrofit.RetrofitInstance
import com.example.solicitarpermisoskotlin.retrofit.RetrofitServices
import com.example.solicitarpermisoskotlin.retrofit.model.MessageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var recycler : RecyclerView
    private lateinit var listaMensajes: ArrayList<MessageResponse>
    private lateinit var adaptadorRecycler: AdaptadorRecycler
    private val PERMISSION = 100
    private val permissions = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
        solicitarPermisos()
        prueba()
    }

    private fun  initialize() {
        recycler = findViewById(R.id.recycler)
        initializeRecyclerView()
    }

    private fun  initializeRecyclerView() {
        val manager = LinearLayoutManager(this)
        listaMensajes = ArrayList()
        adaptadorRecycler = AdaptadorRecycler(listaMensajes)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = manager
        recycler.adapter = adaptadorRecycler


        val dividerItemDecoration = DividerItemDecoration(recycler.context, manager.orientation)
        recycler.addItemDecoration(dividerItemDecoration)
    }

    private fun solicitarPermisos() {
        // if(ActivityCompat.checkSelfPermission())
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_DENIED) {
            alertDialog()
        }
    }

    private fun alertDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Permisos")
        alertDialog.setMessage("Se necesitan permisos para continuar...")
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            getPermissions()
        }
        alertDialog.setNegativeButton("Cancelar", null)
        alertDialog.show()
    }

    private fun getPermissions() {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION);
    }

    private fun showMessage(message: String = "") {
        val message = if (message.isEmpty()) "No se muestra el msj" else message
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION) {
            showMessage("Permisos aceptados")
        }
    }

    private fun prueba() {
        val retrofitServices = RetrofitInstance.getInstance()?.create(RetrofitServices::class.java)
        val responseCall = retrofitServices?.getListaMensajes()

        responseCall?.enqueue(object: Callback<List<MessageResponse>>{
            override fun onResponse(call: Call<List<MessageResponse>>, response: Response<List<MessageResponse>>) {
                if(response.body() != null) {
                    val messages = response.body() as ArrayList<MessageResponse>
                    updateRecycler(messages)
                }
            }

            override fun onFailure(call: Call<List<MessageResponse>>, t: Throwable) {
                println(t)
            }
        })
    }

    private fun updateRecycler(updateMsjs: ArrayList<MessageResponse>) {
        if(updateMsjs.size > 0) {
            listaMensajes.clear()
            listaMensajes.addAll(updateMsjs)
            adaptadorRecycler.notifyDataSetChanged()
        }
    }

    fun search(view: View) {
        val intent = Intent(this, MainActivity2::class.java).apply {

        }
        startActivity(intent)
    }
}

