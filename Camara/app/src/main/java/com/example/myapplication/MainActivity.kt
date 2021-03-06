package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.retrofit.RetrofitInstance
import com.example.myapplication.retrofit.RetrofitServices
import com.example.myapplication.retrofit.model.PhotoResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.RequestBody
import okhttp3.MediaType
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response;
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var imgPhoto : ImageView
    private lateinit var btnPhoto : Button
    private lateinit var btnExport : FloatingActionButton
    private lateinit var imageManager : ImageManager

    private val permission = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.INTERNET
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgPhoto = findViewById(R.id.imageView)
        btnPhoto = findViewById(R.id.btnTakePicture)
        btnExport = findViewById(R.id.btnExport)

        imageManager = ImageManager(this)
        getPermissions()
    }

    fun takePicture(view: View) {
        lifecycleScope.launchWhenStarted {
            if (imageManager.isCreateBaseImageFile()) {
                imageManager.getUri().let { uri ->
                    takeImageResult.launch(uri)
                }
            } else {
                showMessage("No se puede crear la imagen ni el directorio" )

            }
        }
    }

    fun export(view: View?) {
        if (imgPhoto != null) {
            btnExport.setEnabled(false)
            val exportJPG = imageManager
            exportJPG.isCreateBaseImageFile()
      //      showMessage("Archivo JPG exportado")
            uploadImage(exportJPG.getImageToSave())
        } else {
            showMessage("No hay fotos para exportar")
        }
    }

    private fun uploadImage(file: File) {
        val requestFile: RequestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
        val body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile)

        val retroServices: RetrofitServices = RetrofitInstance.getInstance()!!.create(RetrofitServices::class.java)

        val responseCall: Call<PhotoResponse> = retroServices.uploadImage(body)
        responseCall.enqueue(object : Callback<PhotoResponse?> {
            override fun onResponse(call: Call<PhotoResponse?>, response: Response<PhotoResponse?>) {
                if (response.isSuccessful()) {
                    btnExport.setEnabled(true)
                    showMessage("Archivo guardado en el servidor")
                }
            }

            override fun onFailure(call: Call<PhotoResponse?>, t: Throwable) {
                btnExport.setEnabled(true)
                showMessage("Error al subir el archivo")
            }
        })
    }

    private fun preview() {
        val bitmap = imageManager.getThumbnail()
        if (bitmap != null)
            imgPhoto.setImageBitmap(imageManager.getThumbnail())
    }

    private fun saveResizeImage() {
        if (imageManager.isSave()) {
            showMessage("Se guard?? la imagen reducida")
        } else {
            showMessage("No se guard?? la imagen")
        }
    }

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            showMessage("Se tom?? la foto y est?? en raw")
            preview()
            saveResizeImage()
        } else {
            showMessage("")
        }
    }

    private fun getPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            requestMultiplePermissions.launch(permission)
        } else {
            requestMultiplePermissions.launch(permission)
        }
    }

    private val requestMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission: Map<String, Boolean> ->
        {
            permission.entries.forEach() { isGranted ->
                {
                    if (!isGranted.value) {
                        showMessage("Hay un permiso que no se ha otorgado")
                    }
                }
            }
        }
    }

    private fun showMessage(msj : String = "") {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }
}