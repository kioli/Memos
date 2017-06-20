package kioli.memos

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class TabAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val NUMBER_PAGES = 3

    override fun getItem(i: Int): Fragment {
        when (i) {
            0 -> return ViewSpeechToText()
            1 -> return ViewTemp1.getInstance(R.string.tab_dashboard)
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