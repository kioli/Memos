package kioli.memos.db

data class Note(val id: Long, val note: String, var onDeletion: Boolean = false)