package com.example.myclothappnew.util


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object FileHelper {
    fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            val file = File(context.filesDir, "profile_image.jpg") // Saved as "profile_image.jpg"
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            return file.absolutePath // Return saved file path
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun loadImageFromInternalStorage(path: String): Bitmap? {
        return BitmapFactory.decodeFile(path)
    }
}
