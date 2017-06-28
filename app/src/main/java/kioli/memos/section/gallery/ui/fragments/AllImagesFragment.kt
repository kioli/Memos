package com.serveroverload.gallery.ui.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ProgressBar
import android.widget.Toast

import com.example.test.R
import com.serveroverload.gallery.adapter.AllImagesAdapter
import com.serveroverload.gallery.util.GalleryHelper
import com.serveroverload.gallery.util.UtilFunctions
import com.serveroverload.gallery.util.UtilFunctions.AnimationType
import com.twotoasters.jazzylistview.JazzyGridView

/**
 * @author 663918
 */
class AllImagesFragment : Fragment(), OnRefreshListener {

    private var mp: MediaPlayer? = null

    private var gallery: JazzyGridView? = null

    /** The swipe layout.  */
    private var swipeLayout: SwipeRefreshLayout? = null

    /** The double back to exit pressed once.  */
    private var doubleBackToExitPressedOnce: Boolean = false

    /** The m handler.  */
    private val mHandler = Handler()

    /** The m runnable.  */
    private val mRunnable = Runnable { doubleBackToExitPressedOnce = false }

    internal var showingDetail = false

    private val progressBar: ProgressBar? = null

    private var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater!!.inflate(R.layout.home_fragment, container, false)

        activity.title = "Search"

        gallery = rootView!!.findViewById(R.id.listView_products) as JazzyGridView
        // progressBar = (ProgressBar) rootView.findViewById(R.id.loading_bar);

        //gallery.setTransitionEffect(new CardsEffect(24));

        swipeLayout = rootView!!.findViewById(R.id.swipe_container) as SwipeRefreshLayout
        swipeLayout!!.setOnRefreshListener(this@AllImagesFragment)
        swipeLayout!!.setColorSchemeColors(android.R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light)

        mp = MediaPlayer.create(activity, R.raw.type_sound)
        GalleryHelper.gettAllImages(activity)

        setUpGrid(rootView)

        rootView!!.isFocusableInTouchMode = true
        rootView!!.requestFocus()
        rootView!!.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                UtilFunctions.switchContent(R.id.frag_root, UtilFunctions.FULL_SCREEN_GALLERY, activity,
                        AnimationType.SLIDE_DOWN)

            }
            true
        }

        return rootView
    }

    /**
     * @param rootView
     */
    fun setUpGrid(rootView: View) {

        if (!GalleryHelper.allImages.isEmpty()) {

            // Gridview adapter
            rootView.findViewById(R.id.default_nodata).visibility = View.GONE

            swipeLayout!!.visibility = View.VISIBLE

            gallery!!.setAdapter(AllImagesAdapter(activity, GalleryHelper.allImages))

            gallery!!.setOnItemClickListener(OnItemClickListener { arg0, arg1, position, arg3 ->
                Toast.makeText(activity,
                        "position " + position + " " + GalleryHelper.allImages.get(position).imageTitle, Toast.LENGTH_SHORT)
                        .show()

                UtilFunctions.switchFragmentWithAnimation(R.id.frag_root,
                        FullScreenGalleryFragment.newInstance(position, 0, false), activity,
                        UtilFunctions.FULL_SCREEN_GALLERY, AnimationType.SLIDE_RIGHT)

                makeNoice()
            })

        } else {

            rootView.findViewById(R.id.default_nodata).visibility = View.VISIBLE
            swipeLayout!!.visibility = View.GONE

        }
    }

    private fun makeNoice() {

        if (mp!!.isPlaying) {
            mp!!.stop()
            mp!!.release()
            mp = MediaPlayer.create(activity, R.raw.type_sound)
        }

        mp!!.start()
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener#onRefresh
	 * ()
	 */
    override fun onRefresh() {

        swipeLayout!!.isRefreshing = false

        setUpGrid(rootView)

    }

    companion object {

        protected val TAG = AllImagesFragment::class.java.simpleName

        fun newInstance(): Fragment {
            return AllImagesFragment()
        }
    }

}
