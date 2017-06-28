package com.serveroverload.gallery.adapter


import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.bumptech.glide.Glide
import com.example.test.R
import com.serveroverload.gallery.adapter.model.ImageDataModel
import com.serveroverload.gallery.ui.customeview.TextDrawable
import com.serveroverload.gallery.ui.customeview.TextDrawable.IBuilder
import com.serveroverload.gallery.ui.customeview.TouchImageView
import com.serveroverload.gallery.util.ColorGenerator

import java.util.ArrayList

class TouchImageAdapter(private val imageList: ArrayList<ImageDataModel>) : PagerAdapter() {

    private var mDrawableBuilder: IBuilder? = null
    private var drawable: TextDrawable? = null
    private val mColorGenerator = ColorGenerator.MATERIAL

    override fun notifyDataSetChanged() {
        // TODO Auto-generated method stub
        super.notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun getItemPosition(`object`: Any?): Int {

        return super.getItemPosition(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val img = TouchImageView(container.context)

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4).endConfig().roundRect(10)

        drawable = mDrawableBuilder!!.build(imageList[position].imageTitle.get(0).toString(),
                mColorGenerator.getColor(imageList[position].imageTitle))

        Glide.with(container.context).load(imageList[position].imagePath).placeholder(drawable)
                .error(drawable).into(img)

        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

        return img
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

}