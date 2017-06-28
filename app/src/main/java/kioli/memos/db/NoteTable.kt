package kioli.memos.db

import android.database.sqlite.SQLiteDatabase
import kioli.memos.extension.transaction

object NoteTable {

    internal val TABLE_NAME = "notes"
    internal val COLUMN_ID = "_id"
    internal val COLUMN_NOTE = "note"

    private val TABLE_CREATE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COLUMN_NOTE VARCHAR);"

    internal fun onCreate(db: SQLiteDatabase) {
        db.transaction {
            it.execSQL(TABLE_CREATE)
        }
    }

    internal fun onUpgrade(db: SQLiteDatabase) {
        db.transaction {
            it.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
            it.execSQL(TABLE_CREATE)
        }
    }
}