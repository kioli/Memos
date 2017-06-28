package com.serveroverload.gallery.ui.fragments

import com.example.test.R
import com.serveroverload.gallery.adapter.DrawerListArrayAdapter
import com.serveroverload.gallery.ui.customeview.ActionBarDrawerToggle
import com.serveroverload.gallery.ui.customeview.DrawerArrowDrawable
import com.serveroverload.gallery.util.UtilFunctions
import com.serveroverload.gallery.util.UtilFunctions.AnimationType

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.ActionBar
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.widget.DrawerLayout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.ListView

@SuppressLint("ResourceAsColor")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class HomeActivity : FragmentActivity() {

    private var mDrawerLayout: DrawerLayout? = null
    private var mDrawerList: ListView? = null
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var drawerArrow: DrawerArrowDrawable? = null
    private var mDrawerLinear: LinearLayout? = null

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        mDrawerList = findViewById(R.id.left_drawer_child) as ListView

        if (UtilFunctions.isPortrait(applicationContext)) {

            val ab = actionBar
            ab!!.setDisplayHomeAsUpEnabled(true)
            ab.setHomeButtonEnabled(true)

            mDrawerLinear = findViewById(R.id.left_drawer) as LinearLayout
            mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout

            drawerArrow = object : DrawerArrowDrawable(this) {
                override val isLayoutRtl: Boolean
                    get() = false
            }
            mDrawerToggle = object : ActionBarDrawerToggle(this, mDrawerLayout,
                    drawerArrow, R.string.drawer_open, R.string.drawer_close) {

                override fun onDrawerClosed(view: View?) {
                    super.onDrawerClosed(view)
                    invalidateOptionsMenu()
                }

                override fun onDrawerOpened(drawerView: View?) {
                    super.onDrawerOpened(drawerView)
                    invalidateOptionsMenu()
                }
            }
            mDrawerLayout!!.setDrawerListener(mDrawerToggle)
            mDrawerToggle!!.syncState()

        }

        // String imageUrl =
        // PreferenceHelper.getPrefernceHelperInstace(getApplicationContext())
        // .getString(PreferenceHelper.IMAGE_URL, null);

        // if (null != imageUrl) {
        // Picasso.with(getApplicationContext()).load(Uri.parse(imageUrl))
        // .centerCrop().fit()
        // .into((ImageView) findViewById(R.id.profile_pic));
        // }

        UtilFunctions.switchContent(R.id.frag_root,
                UtilFunctions.ALL_IMAGES_TAG, this@HomeActivity,
                AnimationType.SLIDE_LEFT)

        mDrawerList!!.adapter = DrawerListArrayAdapter(
                applicationContext, resources.getStringArray(
                R.array.drawer_list_array))

        mDrawerList!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            mDrawerList!!.setItemChecked(position, true)

            when (position) {
                0 ->

                    UtilFunctions
                            .switchContent(R.id.frag_root,
                                    UtilFunctions.ALL_IMAGES_TAG,
                                    this@HomeActivity,
                                    AnimationType.SLIDE_LEFT)
                1 ->

                    UtilFunctions
                            .switchContent(R.id.frag_root,
                                    UtilFunctions.IMAGE_FOLDERS_TAG,
                                    this@HomeActivity,
                                    AnimationType.SLIDE_LEFT)
                2 -> {
                }
                3 -> {
                }
                4 -> {
                }
                5 ->

                    UtilFunctions
                            .switchContent(R.id.frag_root,
                                    UtilFunctions.SETTINGS_TAG,
                                    this@HomeActivity,
                                    AnimationType.SLIDE_LEFT)
                6 ->

                    UtilFunctions
                            .switchContent(R.id.frag_root,
                                    UtilFunctions.ABOUT_APP_TAG,
                                    this@HomeActivity,
                                    AnimationType.SLIDE_LEFT)
            }// UtilFunctions.switchContent(R.id.frag_root,
            // UtilFunctions.SELECT_IMAGE_FRAGMENT,
            // HomeActivity.this, AnimationType.SLIDE_LEFT);
            //
            // UtilFunctions.switchContent(R.id.frag_root,
            // UtilFunctions.EDIT_IMAGE_FRAGMENT,
            // SampleActivity.this,
            // AnimationType.SLIDE_LEFT);

            if (UtilFunctions.isPortrait(applicationContext)) {

                if (mDrawerLayout!!.isDrawerOpen(mDrawerLinear!!)) {
                    mDrawerLayout!!.closeDrawer(mDrawerLinear)
                } else {
                    mDrawerLayout!!.openDrawer(mDrawerLinear)
                }
            }
        }

        findViewById(R.id.profile_detail).setOnClickListener {
            if (UtilFunctions.isPortrait(applicationContext)) {

                if (mDrawerLayout!!.isDrawerOpen(mDrawerLinear!!)) {
                    mDrawerLayout!!.closeDrawer(mDrawerLinear)
                } else {
                    mDrawerLayout!!.openDrawer(mDrawerLinear)
                }
            }
            //
            // UtilFunctions.switchContent(R.id.frag_root,
            // UtilFunctions.PROFILE_TAG, HomeActivity.this,
            // AnimationType.SLIDE_LEFT);
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == android.R.id.home) {
            if (UtilFunctions.isPortrait(applicationContext)) {

                if (mDrawerLayout!!.isDrawerOpen(mDrawerLinear!!)) {
                    mDrawerLayout!!.closeDrawer(mDrawerLinear)
                } else {
                    mDrawerLayout!!.openDrawer(mDrawerLinear)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (UtilFunctions.isPortrait(applicationContext)) {
            mDrawerToggle!!.syncState()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (UtilFunctions.isPortrait(applicationContext)) {
            mDrawerToggle!!.onConfigurationChanged(newConfig)
        }
    }

    override fun onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed()

    }

    val saveImage: MenuItem? = null

}
