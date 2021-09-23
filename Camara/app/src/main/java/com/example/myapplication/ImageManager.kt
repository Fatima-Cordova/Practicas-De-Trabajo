package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.*

class ImageManager {

    companion object{
        private const val EXTENSION : String = ".jpg"
        const val IMAGE_TEMP : String = "camera_photo${EXTENSION}"

        private const val FILE_EXPORT : String = "file_export.jpg"

        const val IMAGE_QUALITY_TO_SAVE : Int = 65
        const val IMAGE_WIDTH_TO_SAVE : Float = 480F

        const val IMAGE_TEMP_TO_SAVE : String = "resize_camera_photo${EXTENSION}"

        const val IMAGE_QUALITY_TO_WORK  : Int = 100
        const val IMAGE_WIDTH_TO_WORK : Float = 720F
    }

    private lateinit var context : Context
    private lateinit var directory : File
    private var imageFile : String = ""
    private var imageToSave : String = ""


    constructor(con: Context) {
        this.context = con
        this.directory = File(this.context.getExternalFilesDir(null), Environment.DIRECTORY_PICTURES)
        this.imageFile = directory.absolutePath.toString()
        this.imageFile += "/${IMAGE_TEMP}"


        this.imageToSave = directory.absolutePath.toString()
        this.imageToSave += "/${IMAGE_TEMP_TO_SAVE}"

    }

    fun getImageToSave(): File {
        return File(this.imageToSave)
    }

    fun getImagePath(): String {
        return this.imageFile
    }

    fun createJPG() {
        if (isBaseDirectoryExist()) {
            try {
                val fileName: String = directory.getAbsolutePath()
                    .toString() + "/" + System.currentTimeMillis() + FILE_EXPORT

                imageToSave = File(fileName).toString();

            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
        }
    }

    fun isSave() : Boolean {
        return try {
            var bitmap = BitmapFactory.decodeFile(imageFile)
            bitmap = resize (bitmap, IMAGE_WIDTH_TO_SAVE)
            bitmap = portraitOrientation(bitmap)
            FileOutputStream(imageToSave).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY_TO_SAVE, out)
                out.close()
            }
            true
        } catch (ex : IOException) {
            false
        }
    }

    fun getThumbnail() : Bitmap? {
        return try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            var bitmap = BitmapFactory.decodeFile(imageFile)
            bitmap = resize(bitmap, IMAGE_WIDTH_TO_SAVE)
            bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY_TO_SAVE, byteArrayOutputStream)
            bitmap
        } catch (ex : Exception) {
            null
        }
    }

    private fun resize(bitmap: Bitmap, newWidth : Float) : Bitmap {
        val width = bitmap.width
        val heigth = bitmap.height
        val aspectRatio : Float = newWidth / width.toFloat()
        if (width > newWidth) {
            return Bitmap.createScaledBitmap(bitmap, newWidth.toInt(), (heigth.toFloat() * aspectRatio).toInt(), false)
        }
        return bitmap
    }


    private fun portraitOrientation(bitmap: Bitmap) : Bitmap {
        val ei : ExifInterface = ExifInterface(this.imageFile)
        when (ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                return rotate(bitmap, 90f)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                return rotate(bitmap, 270f)
            }
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                return rotate(bitmap, 270f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                return rotate(bitmap, 270f)
            }
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> {
                return flip(bitmap, true, vertical = false)
            }
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                return flip(bitmap, false, vertical = true)
            }
            else -> {
                return bitmap
            }
        }
    }

    private fun rotate(bitmap : Bitmap, degress : Float) : Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degress)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun flip(bitmap: Bitmap, horizontal : Boolean, vertical : Boolean) : Bitmap {
        val matrix = Matrix()
        matrix.preScale(if(horizontal) (-1f) else 1f, if (vertical) (-1f) else 1f)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun getUri() : Uri? {
        return getUriFromPath(this.context)
    }

    private fun getUriFromPath(context: Context) : Uri {
        return FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.fileprovider", File(this.imageFile))
    }

    fun isCreateBaseImageFile() : Boolean {
        return try {
            val image = File(imageFile)
            if (isBaseDirectoryExist()) {
                if (image.exists()) {
                    if (image.delete()) {
                        image.createNewFile()
                    } else {
                        false
                    }
                } else {
                    image.createNewFile()
                }
            } else {
                false
            }
        } catch (ex : IOException) {
            false
        }
    }

    private fun isBaseDirectoryExist() : Boolean {
        return if(directory.exists()) {
            true
        } else {
            try {
                directory.mkdir()
            } catch (ex : IOException) {
                false
            }
        }
    }
}