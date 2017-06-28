package kioli.memos.extension

import android.os.Environment
import com.simplemobiletools.gallery.helpers.NOMEDIA
import java.io.File

fun File.containsNoMedia() = isDirectory && File(this, NOMEDIA).exists()

fun File.isDownloadsFolder() = absolutePath == Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
