package com.example.camara

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {

    private lateinit var imgPhoto : ImageView
    private lateinit var btnPhoto : Button
    private lateinit var imageManager : ImageManager

    private val permission = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgPhoto = findViewById(R.id.imageView)
        btnPhoto = findViewById(R.id.btnTakePicture)

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

    private fun preview() {
        val bitmap = imageManager.getThumbnail()
        if (bitmap != null)
        imgPhoto.setImageBitmap(imageManager.getThumbnail())
    }

    private fun saveResizeImage() {
        if (imageManager.isSave()) {
            showMessage("Se guardó la imagen reducida")
        } else {
            showMessage("No se guardó la imagen")
        }
    }

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            showMessage("Se tomó la foto y está en raw")
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