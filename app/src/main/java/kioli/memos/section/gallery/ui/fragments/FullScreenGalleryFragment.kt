package com.serveroverload.gallery.ui.fragments

import java.io.File
import java.io.IOException

import com.drew.imaging.ImageMetadataReader
import com.drew.imaging.ImageProcessingException
import com.drew.metadata.Directory
import com.drew.metadata.Metadata
import com.drew.metadata.Tag
import com.example.test.R
import com.serveroverload.gallery.adapter.TouchImageAdapter
import com.serveroverload.gallery.ui.customeview.ExtendedViewPager
import com.serveroverload.gallery.util.GalleryHelper
import com.serveroverload.gallery.util.UtilFunctions
import com.serveroverload.gallery.util.UtilFunctions.AnimationType

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast

class FullScreenGalleryFragment : Fragment() {
    private var mViewPager: ExtendedViewPager? = null
    private var touchImageAdapter: TouchImageAdapter? = null
    private var isFromFolders: Boolean = false
    private var currentFolderPosition: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.doodle_gallery, container, false)

        if (null != arguments) {
            currentPagePosition = arguments.getInt(IMAGE_POSITION)
            currentFolderPosition = arguments.getInt(FOLDER_POSITION)
            isFromFolders = arguments.getBoolean(IS_FROM_FOLDER)

            // setContentView(R.layout.activity_viewpager_example);
            mViewPager = rootView.findViewById(R.id.view_pager) as ExtendedViewPager

            if (isFromFolders) {

                touchImageAdapter = TouchImageAdapter(
                        GalleryHelper.imageFolderMap.get(GalleryHelper.keyList.get(currentFolderPosition)))
            } else {

                touchImageAdapter = TouchImageAdapter(GalleryHelper.allImages)

            }

            mViewPager!!.setAdapter(touchImageAdapter)

            mViewPager!!.setCurrentItem(currentPagePosition)

            rootView.findViewById(R.id.edit).setOnClickListener(OnClickListener { })

            rootView.findViewById(R.id.info).setOnClickListener(OnClickListener {
                // 1. Instantiate an AlertDialog.Builder with its
                // constructor
                val builder = AlertDialog.Builder(activity)

                builder.setPositiveButton("OK") { dialog, id -> }
                builder.setNegativeButton("Done") { dialog, id ->
                    // User cancelled the dialog
                }

                val str = StringBuilder()

                // 2. Chain together various setter methods to set the
                // dialog characteristics

                // 3. Get the AlertDialog from create()

                // There are multiple ways to get a Metadata object for
                // a file

                //
                // SCENARIO 1: UNKNOWN FILE TYPE
                //
                // This is the most generic approach. It will
                // transparently determine the file type and invoke the
                // appropriate
                // readers. In most cases, this is the most appropriate
                // usage. This will handle JPEG, TIFF, GIF, BMP and RAW
                // (CRW/CR2/NEF/RW2/ORF) files and extract whatever
                // metadata is available and understood.

                //

                val file = imageFile

                try {
                    val metadata = ImageMetadataReader.readMetadata(file)

                    for (directory in metadata.getDirectories()) {

                        //
                        // Each Directory stores values in Tag objects
                        //
                        for (tag in directory.getTags()) {
                            System.out.println(tag)

                            str.append(tag.toString())
                            str.append("\n")

                        }

                        //
                        // Each Directory may also contain error
                        // messages
                        //
                        if (directory.hasErrors()) {
                            for (error in directory.getErrors()) {
                                System.err.println("ERROR: " + error)
                            }
                        }

                    }

                } catch (e: ImageProcessingException) {
                    // handle exception
                } catch (e: IOException) {
                    // handle exception
                }

                builder.setMessage(str.toString()).setTitle("Image Info")

                val dialog = builder.create()

                dialog.show()
            })

            rootView.findViewById(R.id.canvas).setOnClickListener(OnClickListener {
                // AppContants.doodleImageURL =
                // DiskUtil.getListOfDoodles(true).get(mViewPager.getCurrentItem());
                //
                // UtilFunctions.switchFragmentWithAnimation(R.id.frag_root,
                // DoodleFragment.newInstance(), getActivity(),
                // UtilFunctions.DOODLE_FRAGMENT,
                // AnimationType.SLIDE_RIGHT);
            })

            rootView.findViewById(R.id.delete).setOnClickListener(OnClickListener {
                // // Set up the projection (we only need the ID)
                // String[] projection = { MediaStore.Images.Media._ID };
                //
                // // Match on the file path
                // String selection = MediaStore.Images.Media.DATA + " = ?";
                // String[] selectionArgs = new String[] {
                // new
                // File(DiskUtil.getListOfDoodles(true).get(mViewPager.getCurrentItem())).getAbsolutePath()
                // };
                //
                // // Query for the ID of the media matching the file path
                // Uri queryUri =
                // MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                // ContentResolver contentResolver =
                // getActivity().getContentResolver();
                // Cursor c = contentResolver.query(queryUri, projection,
                // selection, selectionArgs, null);
                // if (c.moveToFirst()) {
                // // We found the ID. Deleting the item via the content
                // // provider will also remove the file
                // long id =
                // c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                // Uri deleteUri =
                // ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                // id);
                // contentResolver.delete(deleteUri, null, null);
                // Toast.makeText(getActivity(), "Deleted", 500).show();
                // } else {
                // Toast.makeText(getActivity(), "Failed to Delete",
                // 500).show();
                // }
                // c.close();

                val file = imageFile

                // File f = new
                // File(GalleryHelper.imageFolderMap.get(GalleryHelper.keyList.get(currentPagePosition))
                // .get(mViewPager.getCurrentItem()).getImagePath());

                if (file.delete()) {

                    touchImageAdapter!!.notifyDataSetChanged()

                    activity
                            .sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                    Uri.fromFile(File(GalleryHelper.imageFolderMap
                                            .get(GalleryHelper.keyList.get(currentPagePosition))
                                            .get(mViewPager!!.getCurrentItem()).imagePath))))

                    Toast.makeText(activity, "Deleted", 500).show()
                } else {
                    Toast.makeText(activity, "Failed to Delete", 500).show()
                }
            })

            rootView.findViewById(R.id.share).setOnClickListener(OnClickListener {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "image/jpeg"

                if (isFromFolders) {
                    share.putExtra(Intent.EXTRA_STREAM,
                            Uri.parse(
                                    GalleryHelper.imageFolderMap.get(GalleryHelper.keyList.get(currentPagePosition))
                                            .get(mViewPager!!.getCurrentItem()).imagePath))
                } else {

                    share.putExtra(Intent.EXTRA_STREAM,
                            Uri.parse(GalleryHelper.allImages.get(mViewPager!!.getCurrentItem()).imagePath))

                }
                startActivity(Intent.createChooser(share, "Share Image"))
            })

            rootView.setFocusableInTouchMode(true)
            rootView.requestFocus()
            rootView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (isFromFolders) {

                        //							UtilFunctions.switchFragmentWithAnimation(R.id.frag_root,
                        //									ImagesInFolder.newInstance(folderIndex), getActivity(),
                        //									UtilFunctions.IMAGE_FOLDERS_TAG, AnimationType.SLIDE_RIGHT);

                        UtilFunctions.switchFragmentWithAnimation(R.id.frag_root,
                                ImagesInFolder.newInstance(currentPagePosition), activity,
                                UtilFunctions.IMAGE_FOLDERS_TAG, AnimationType.SLIDE_RIGHT)

                    } else {

                        UtilFunctions.switchContent(R.id.frag_root, UtilFunctions.ALL_IMAGES_TAG,
                                activity as HomeActivity, AnimationType.SLIDE_RIGHT)
                    }

                }
                true
            })
        }

        return rootView
    }

    private val imageFile: File
        get() {
            val file: File

            if (isFromFolders) {
                file = File(GalleryHelper.imageFolderMap.get(GalleryHelper.keyList.get(currentPagePosition))
                        .get(mViewPager!!.getCurrentItem()).imagePath)
            } else {
                file = File(GalleryHelper.allImages.get(mViewPager!!.getCurrentItem()).imagePath)
            }
            return file
        }

    companion object {

        private val IMAGE_POSITION = "PagerPosition"
        private val IS_FROM_FOLDER = "IsFromFolders"
        private val FOLDER_POSITION = "FolderPosition"
        private var currentPagePosition = 0

        fun newInstance(imagePosition: Int, folderPosition: Int, isFromFolders: Boolean): Fragment {

            val bundle = Bundle()
            bundle.putBoolean(IS_FROM_FOLDER, isFromFolders)
            bundle.putInt(IMAGE_POSITION, imagePosition)
            bundle.putInt(FOLDER_POSITION, folderPosition)
            val fullScreenPagerFragment = FullScreenGalleryFragment()
            fullScreenPagerFragment.arguments = bundle
            return fullScreenPagerFragment
        }
    }

}