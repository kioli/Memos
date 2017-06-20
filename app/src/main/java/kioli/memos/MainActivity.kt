package kioli.memos

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                pager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                pager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                pager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private val mPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(position: Int) {
            // Do nothing
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            // Do nothing
        }

        override fun onPageSelected(position: Int) {
            when (position) {
                0 -> navigation?.selectedItemId = R.id.navigation_home
                1 -> navigation?.selectedItemId = R.id.navigation_dashboard
                2 -> navigation?.selectedItemId = R.id.navigation_notifications
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        pager.adapter = TabAdapter(supportFragmentManager)
        pager.addOnPageChangeListener(mPageChangeListener)
    }
}
