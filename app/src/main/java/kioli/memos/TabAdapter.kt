package kioli.memos

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import kioli.memos.section.gallery.ViewGallery
import kioli.memos.section.notes.ViewSpeechToText

class TabAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val NUMBER_PAGES = 3

    override fun getItem(i: Int): Fragment {
        when (i) {
            0 -> return ViewSpeechToText()
            1 -> return ViewGallery()
            else -> return ViewTemp1.getInstance(R.string.tab_notifications)
        }
    }

    override fun getCount(): Int {
        return NUMBER_PAGES
    }

    override fun getPageTitle(position: Int): CharSequence {
        return (position + 1).toString()
    }
}