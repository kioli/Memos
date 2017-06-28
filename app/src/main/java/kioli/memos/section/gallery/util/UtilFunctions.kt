package kioli.memos.section.gallery.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Vibrator
import kioli.memos.R

// TODO: Auto-generated Javadoc
/**
 * The Class UtilFunctions.
 */
@SuppressLint("NewApi")
object UtilFunctions {

    private var CURRENT_TAG: String? = null

    val ALL_IMAGES_TAG = "HomeFragment"
    val SETTINGS_TAG = "Settings"
    val FULL_SCREEN_GALLERY = "DoodleDetail"
    val IMAGE_FOLDERS_TAG = "Doodle"
    val ABOUT_APP_TAG = "About"

    /** The Constant IS_ISC.  */
    // public static final boolean IS_JBMR2 = Build.VERSION.SDK_INT ==
    // Build.VERSION_CODES.JELLY_BEAN_MR2;
    val IS_ISC = Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH

    /** The Constant IS_GINGERBREAD_MR1.  */
    val IS_GINGERBREAD_MR1 = Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD_MR1


    fun getVersion(context: Context): String {
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName,
                    PackageManager.GET_META_DATA)
            return pInfo.versionCode.toString() + " " + pInfo.versionName

        } catch (e: NameNotFoundException) {
            return "1.0.1"
        }

    }


    /**
     * The Enum AnimationType.
     */
    enum class AnimationType {

        /** The slide left.  */
        SLIDE_LEFT,
        /** The slide right.  */
        SLIDE_RIGHT,
        /** The slide up.  */
        SLIDE_UP,
        /** The slide down.  */
        SLIDE_DOWN,
        /** The fade in.  */
        FADE_IN,
        /** The slide in slide out.  */
        SLIDE_IN_SLIDE_OUT,
        /** The fade out.  */
        FADE_OUT
    }

    /**
     * Vibrate.

     * @param context
     * *            the context
     */
    fun vibrate(context: Context) {
        // Get instance of Vibrator from current Context and Vibrate for 400
        // milliseconds
        (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(100)
    }

    fun isPortrait(context: Context): Boolean {

        return context.resources.getBoolean(R.bool.is_portrait)
    }

}
