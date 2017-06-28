package com.serveroverload.gallery.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.example.test.R
import com.serveroverload.gallery.ui.customeview.TextDrawable
import com.serveroverload.gallery.ui.customeview.TextDrawable.IBuilder
import com.serveroverload.gallery.util.ColorGenerator
import com.serveroverload.gallery.util.GalleryHelper

/**
 * The Class ImageAdapter.
 */
class ImageFoldersAdapter(private val context: Activity) : BaseAdapter() {

    private var mDrawableBuilder: IBuilder? = null
    private var drawable: TextDrawable? = null
    private val mColorGenerator = ColorGenerator.MATERIAL

    override fun getCount(): Int {
        return GalleryHelper.keyList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {

            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            convertView = inflater.inflate(R.layout.folder_item, null)
        }

        (convertView!!.findViewById(R.id.textView) as TextView).setText(GalleryHelper.keyList.get(position))

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4).endConfig().roundRect(10)

        drawable = mDrawableBuilder!!.build(GalleryHelper.keyList.get(position).get(0).toString(),
                mColorGenerator.getColor(GalleryHelper.keyList.get(position)))

        Glide.with(context)
                .load(GalleryHelper.imageFolderMap.get(GalleryHelper.keyList.get(position)).get(0).imagePath)
                .placeholder(drawable).centerCrop().error(drawable).animate(R.anim.slide_up)
                .into(convertView.findViewById(R.id.imageView) as ImageView)

        return convertView
    }

}