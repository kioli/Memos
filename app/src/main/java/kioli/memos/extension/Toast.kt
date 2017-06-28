package kioli.memos.extension

import android.content.Context
import android.widget.Toast

fun Context.toastShort(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Context.toastLong(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
