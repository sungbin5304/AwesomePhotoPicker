package me.sungbin.awesomephotopicker.library.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore


/**
 * Created by SungBin on 2020-10-16.
 */

object PhotoUtil {
    fun getAllPath(
        context: Context,
        photoFilter: PhotoFilter? = null // todo: filter
    ): ArrayList<Uri> {
        val uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?
        val list = ArrayList<Uri>()
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC"
        cursor = context.contentResolver.query(uriExternal, projection, null, null, orderBy)
        cursor?.let {
            val columnIndexId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            var index = 0
            while (cursor.moveToNext()) {
                val imageId = cursor.getString(columnIndexId)
                val imageUri = Uri.withAppendedPath(uriExternal, imageId)
                list.add(imageUri)
                index++
                if (index > 18) break
            }
            cursor.close()
        }
        return list
    }

    private fun getFileExtension(filePath: String) =
        filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length)

    fun createCustomFileFilter() = true

    @Suppress("DEPRECATION")
    fun convertUriToPath(context: Context, contentUri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        return context.contentResolver.query(contentUri, projection, null, null, null)
            ?.use { cursor ->
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                cursor.getString(columnIndex)
            }
    }
}