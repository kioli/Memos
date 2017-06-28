package com.serveroverload.gallery.adapter

import java.util.ArrayList

import com.example.test.R
import com.serveroverload.gallery.ui.customeview.TextAwesome

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

// TODO: Auto-generated Javadoc
/**
 * The Class DrawerListArrayAdapter.
 */
class DrawerListArrayAdapter
/**
 * Instantiates a new drawer list array adapter.

 * @param context
 * *            the context
 * *
 * @param values
 * *            the values
 */
(
        /** The context.  */
        private val context: Context,
        /** The values.  */
        private val values: Array<String>) : ArrayAdapter<String>(context, -1, values) {

    /** The default tone.  */
    private val defaultTone = Color.parseColor("#bf360c")

    /** The tone.  */
    private val tone = 1.0f

    /** The tones.  */
    private val tones = ArrayList<Int>()
    private val icons = ArrayList<Int>()

    private var holder: ViewHolder? = null

    private fun getTone() {

        tones.add(Color.parseColor("#757575"))
        tones.add(Color.parseColor("#616161"))

        tones.add(Color.parseColor("#757575"))
        tones.add(Color.parseColor("#616161"))

        tones.add(Color.parseColor("#757575"))
        tones.add(Color.parseColor("#616161"))

        tones.add(Color.parseColor("#757575"))

    }

    private fun getIcons() {

        icons.add(R.string.fa_folder_open)
        icons.add(R.string.fa_paint_brush)
        icons.add(R.string.fa_magic)
        icons.add(R.string.fa_magnet)
        icons.add(R.string.fa_bookmark)
        icons.add(R.string.fa_gears)
        icons.add(R.string.fa_clipboard)

    }

    init {

        getTone()
        getIcons()

    }

    /*
	 * (non-Javadoc)
	 *
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var rowView: View = convertView
        if (convertView == null) {
            val inflator = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflator
                    .inflate(R.layout.drawer_list_item, parent, false)
            holder = ViewHolder()
            holder!!.title = rowView.findViewById(android.R.id.title) as TextView
            holder!!.icon = rowView.findViewById(R.id.icon) as TextAwesome

            rowView.tag = holder
        } else {
            holder = rowView.tag as ViewHolder
        }

        rowView.setBackgroundColor(tones[position])
        holder!!.title!!.text = values[position]
        holder!!.icon!!.setText(icons[position])

        return rowView
    }

    internal inner class ViewHolder {
        var title: TextView? = null
        var icon: TextAwesome? = null
    }
}
