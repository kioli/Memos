package kioli.memos.section.gallery

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kioli.memos.R
import kotlinx.android.synthetic.main.view_gallery.*

class ViewGallery : Fragment() {

    private var items: ArrayList<GalleryItem> = ArrayList()

    var listener = { image: GalleryItem ->
        // todo something
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.view_gallery, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_photos.setHasFixedSize(true)
        list_photos.layoutManager = GridLayoutManager(context, 3)
        list_photos.adapter = ImageAdapter(items, listener)
    }

    override fun onResume() {
        super.onResume()
        tryLoadGallery()
    }

    private fun tryLoadGallery() {
        var map = GalleryHelper.getImageFolderMap(activity)
        var images = GalleryHelper.gettAllImages(activity)

//        val dirs = getCachedDirectories()
//        if (dirs.isNotEmpty() && !mLoadedInitialPhotos) {
//            gotDirectories(dirs)
//        }
//
//        if (!mLoadedInitialPhotos) {
//            directories_refresh_layout.isRefreshing = true
//        }
//
//        mLoadedInitialPhotos = true
//        mCurrAsyncTask = GetDirectoriesAsynctask(applicationContext, mIsPickVideoIntent || mIsGetVideoContentIntent, mIsPickImageIntent || mIsGetImageContentIntent) {
//            gotDirectories(it)
//        }
//        mCurrAsyncTask!!.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
//
//
//
//        val token = object : TypeToken<List<Medium>>() {}.type
//        val media = Gson().fromJson<ArrayList<Medium>>(config.loadFolderMedia(mPath), token) ?: ArrayList<Medium>(1)
//        if (media.isNotEmpty() && !mLoadedInitialPhotos) {
//            gotMedia(media)
//        } else {
//            media_refresh_layout.isRefreshing = true
//        }
//
//        mLoadedInitialPhotos = true
//        GetMediaAsynctask(context.applicationContext, mPath, mIsGetVideoIntent, mIsGetImageIntent, mShowAll) {
//            gotMedia(it)
//        }.execute()
    }

//    private fun getThumbnails() {
//        val projection = arrayOf(MediaStore.Images.Thumbnails.DATA)
//        val cursor = activity.contentResolver.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null)
//        val columnIndexPhoto = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA)
//        (1 .. cursor.count).map {
//            cursor.moveToNext()
//            val photoPath = cursor.getString(columnIndexPhoto)
//            items.add(GalleryItem(File(photoPath)))
//        }
//        cursor.close()
//    }
//
//    private fun getImages() {
//        val projection = arrayOf(MediaStore.Images.Media.DATA)
//        val orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
//        val cursor = activity.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, orderBy)
//        val columnIndexPhoto = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
//        (1 .. cursor.count).map {
//            cursor.moveToNext()
//            val photoPath = cursor.getString(columnIndexPhoto)
//            items.add(GalleryItem(File(photoPath)))
//        }
//        cursor.close()
//    }

}
