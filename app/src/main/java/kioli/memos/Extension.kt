package kioli.memos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import kioli.memos.db.DbHelper

// TOAST
fun Context.toastShort(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Context.toastLong(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

// CONTEXT
val Context.database: DbHelper
    get() = DbHelper.getInstance(applicationContext)

// DB
inline fun <T> SQLiteDatabase.transaction(func: (SQLiteDatabase) -> T): T {
    beginTransaction()
    try {
        val result = func(this)
        setTransactionSuccessful()
        return result
    } finally {
        endTransaction()
    }
}