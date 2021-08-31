package com.example.solicitarpermisoskotlin

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.solicitarpermisoskotlin.retrofit.RetrofitInstance
import com.example.solicitarpermisoskotlin.retrofit.RetrofitServices
import com.example.solicitarpermisoskotlin.retrofit.model.MessageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var btnEnviar: Button
    private val PERMISSION = 100
    private lateinit var listaMensajes: ArrayList<MessageResponse>
    private val permissions = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEnviar = findViewById(R.id.btnEnviar)
        solicitarPermisos()
        btnEnviar.setOnClickListener {
        }
        prueba()
        listaMensajes = ArrayList()
    }

    private fun solicitarPermisos() {
        // if(ActivityCompat.checkSelfPermission())
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_DENIED
        ) {
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
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
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
                    listaMensajes = response.body() as ArrayList<MessageResponse>
                }
            }

            override fun onFailure(call: Call<List<MessageResponse>>, t: Throwable) {
                println(t)
            }

        })

    }
}












/*

   if (ActivityCompat.shouldShowRequestPermissionRationale
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
            (ActivityCompat.shouldShowRequestPermissionRationale
                (this, Manifest.permission.READ_EXTERNAL_STORAGE))) {

        }



         private fun solicitarPermisos() {
        // if(ActivityCompat.checkSelfPermission())
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
            (ActivityCompat.shouldShowRequestPermissionRationale
                (this, Manifest.permission.READ_EXTERNAL_STORAGE))) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Se necesitan permisos para continuar...")
            builder.setPositiveButton(android.R.string.ok) {
                    dialog, which -> Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                ActivityCompat.requestPermissions(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            builder.setNegativeButton("Cancelar", null)
            builder.show()
        }
    }


        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }*/


/*     if (escribir == PackageManager.PERMISSION_GRANTED && lectura == PackageManager.PERMISSION_GRANTED) {
    }
        if (ActivityCompat.shouldShowRequestPermissionRationale
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
            (ActivityCompat.shouldShowRequestPermissionRationale
            (this, Manifest.permission.READ_EXTERNAL_STORAGE))) {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Se necesitan permisos de lectura y escritura para continuar...");
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });

            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ActivityCompat.requestPermissions(InicioActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, codigo);
                    ActivityCompat.requestPermissions(InicioActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, codigo);
                }
            });
            builder.show();
        }
    }

        else{
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, codigo);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, codigo);
    }*/