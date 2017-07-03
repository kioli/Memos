package kioli.memos.section.gallery

import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.util.*
import kotlin.collections.ArrayList

object GalleryHelper {

    val imageFolderMap: MutableMap<String, ArrayList<ImageDataModel>> = HashMap()
    val keyList = ArrayList<String>()
    val allImages = ArrayList<ImageDataModel>()

    /**
     * Getting All Images Path.
     *
     * @param activity
     * *            the activity
     * *
     * @return ArrayList with images Path
     */
    fun getImageFolderMap(activity: Activity): Map<String, ArrayList<ImageDataModel>> {
        imageFolderMap.clear()

        var uri: Uri
        var cursor: Cursor
        var column_index_data: Int
        var column_index_folder_name: Int

        var absolutePathOfImage: String? = null
        var folderName: String
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        cursor = activity.contentResolver.query(uri, projection, null, null, null)

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data)

            folderName = cursor.getString(column_index_folder_name)

            val imDataModel = ImageDataModel(folderName, absolutePathOfImage)

            if (imageFolderMap.containsKey(folderName)) {

                imageFolderMap[folderName]?.add(imDataModel)

            } else {

                val listOfAllImages = ArrayList<ImageDataModel>()

                listOfAllImages.add(imDataModel)

                imageFolderMap.put(folderName, listOfAllImages)
            }
        }

        // Get all Internal images
        uri = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI

        cursor = activity.contentResolver.query(uri, projection, null, null, null)

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data)

            folderName = cursor.getString(column_index_folder_name)

            val imDataModel = ImageDataModel(folderName,
                    absolutePathOfImage)

            if (imageFolderMap.containsKey(folderName)) {

                imageFolderMap[folderName]?.add(imDataModel)
            } else {

                val listOfAllImages = ArrayList<ImageDataModel>()

                listOfAllImages.add(imDataModel)

                imageFolderMap.put(folderName, listOfAllImages)
            }

        }

        keyList.clear()
        keyList.addAll(imageFolderMap.keys)

        return imageFolderMap
    }

    /**
     * Getting All Images Path.

     * @param activity
     * *            the activity
     * *
     * @return ArrayList with images Path
     */
    fun gettAllImages(activity: Activity): ArrayList<ImageDataModel> {

        val processedFilesArray = ArrayList<Hashtable<String, ArrayList<String>>>()

        allImages.clear()
        var uri: Uri
        var cursor: Cursor
        var column_index_data: Int
        var column_index_folder_name: Int

        var absolutePathOfImage: String? = null
        var imageName: String
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        cursor = activity.contentResolver.query(uri, projection, null, null, null)

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data)

            imageName = cursor.getString(column_index_folder_name)

            allImages.add(ImageDataModel(imageName, absolutePathOfImage))

        }

        // Get all Internal images
        uri = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI

        cursor = activity.contentResolver.query(uri, projection, null, null, null)

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data)

            imageName = cursor.getString(column_index_folder_name)

            allImages.add(ImageDataModel(imageName, absolutePathOfImage))
        }

        return allImages
    }

}
