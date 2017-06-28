package kioli.memos.db

import android.content.ContentValues
import android.content.Context
import kioli.memos.extension.database
import kioli.memos.extension.transaction

class NoteDataSource {

    companion object {
        /**
         * Method to insert a Note in the DB
         * @param context   context to access the DB
         * @param note      note the user inserted
         * @return the ID of the newly inserted Note, or -1 if an error occurred
         */
        fun insert(context: Context, note: String): Long {
            var result: Long = -1
            context.database.writableDatabase.transaction {
                val values = ContentValues()
                values.put(NoteTable.COLUMN_NOTE, note)
                result = it.insert(NoteTable.TABLE_NAME, null, values)
                if (result >= 0) {
                    val projection = arrayOf(NoteTable.COLUMN_ID)
                    val cursor = it.query(NoteTable.TABLE_NAME, projection, null, null, null, null, null)
                    if (cursor != null) {
                        while (cursor.moveToLast()) {
                            result = cursor.getLong(0)
                        }
                        cursor.close()
                    }
                }
            }
            return result
        }

        /**
         * Method to update a Note in the DB
         * @param context   context to access the DB
         * @param note      note the user inserted
         * @return the number of rows in the DB that got updated
         */
        fun update(context: Context, note: String): Int {
            var result = -1
            context.database.writableDatabase.transaction {
                val values = ContentValues()
                values.put(NoteTable.COLUMN_NOTE, note)
                val selection = "${NoteTable.COLUMN_NOTE} = ? "
                val selectionArgs = arrayOf(note)
                result = it.update(NoteTable.TABLE_NAME, values, selection, selectionArgs)
            }
            return result
        }

        /**
         * Method to get all Notes from the DB
         *
         * @param context    context to access the DB
         * @return the Notes present on the DB
         */
        fun getNotes(context: Context): ArrayList<Note> {
            val result = ArrayList<Note>()
            context.database.writableDatabase.transaction {
                val projection = arrayOf(NoteTable.COLUMN_ID, NoteTable.COLUMN_NOTE)
                val cursor = it.query(NoteTable.TABLE_NAME, projection, null, null, null, null, null)
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        result.add(Note(cursor.getLong(0), cursor.getString(1)))
                    }
                    cursor.close()
                }
            }
            return result
        }

        /**
         * Method to delete a Note from the DB
         * @param context    context to access the DB
         * @param noteId     ID of the Note we want to delete in the DB
         * *
         * @return the number of rows in the DB that got deleted
         */
        fun delete(context: Context, noteId: Long): Int {
            var result = -1
            context.database.writableDatabase.transaction {
                result = it.delete(NoteTable.TABLE_NAME, "${NoteTable.COLUMN_ID} = ?", arrayOf(noteId.toString()))
            }
            return result
        }
    }
}