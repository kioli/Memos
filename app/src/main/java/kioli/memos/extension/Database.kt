package kioli.memos.extension

import android.database.sqlite.SQLiteDatabase

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