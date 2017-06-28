package com.serveroverload.gallery.adapter

import java.util.ArrayList

import com.bumptech.glide.Glide
import com.example.test.R
import com.serveroverload.gallery.adapter.model.ImageDataModel
import com.serveroverload.gallery.ui.customeview.TextDrawable
import com.serveroverload.gallery.ui.customeview.TextDrawable.IBuilder
import com.serveroverload.gallery.util.ColorGenerator

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * The Class ImageAdapter.
 */
class AllImagesAdapter(private val context: Activity, private val images: ArrayList<ImageDataModel>) : BaseAdapter() {

    private var mDrawableBuilder: IBuilder? = null
    private var drawable: TextDrawable? = null
    private val mColorGenerator = ColorGenerator.MATERIAL

    override fun getCount(): Int {
        return images.size
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

            convertView = inflater.inflate(R.layout.gallery_item, null)
        }

        (convertView!!.findViewById(R.id.textView) as TextView).setText(images[position].imageTitle)

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4).endConfig().roundRect(10)

        drawable = mDrawableBuilder!!.build(images[position].imageTitle.get(0).toString(),
                mColorGenerator.getColor(images[position].imageTitle))

        Glide.with(context).load(images[position].imagePath).placeholder(drawable).error(drawable).centerCrop().animate(R.anim.slide_up)
                .into(convertView.findViewById(R.id.imageView) as ImageView)

        return convertView
    }

}