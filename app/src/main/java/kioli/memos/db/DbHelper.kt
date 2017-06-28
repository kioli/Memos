package kioli.memos.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kioli.memos.extension.transaction

class DbHelper(context: Context) : SQLiteOpenHelper(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "usabilla.db"
        private val TABLES_LIST = arrayOf(NoteTable.TABLE_NAME)

        private var instance: DbHelper? = null

        @Synchronized
        fun getInstance(context: Context): DbHelper {
            if (instance == null) {
                instance = DbHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        NoteTable.onCreate(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        NoteTable.onUpgrade(db)
    }

    fun clearDb() {
        readableDatabase.transaction {
            for (tableName in TABLES_LIST) {
                it.delete(tableName, null, null)
            }
        }
    }
}