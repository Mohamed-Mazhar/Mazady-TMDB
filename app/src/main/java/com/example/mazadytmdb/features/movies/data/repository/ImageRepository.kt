package com.example.mazadytmdb.features.movies.data.repository

import android.content.Context
import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ImageRepository(private val context: Context) {

    suspend fun saveImage(bitmap: Bitmap, filename: String): String {
        return withContext(Dispatchers.IO) {
            val file = File(context.filesDir, filename)
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            file.absolutePath
        }
    }


}