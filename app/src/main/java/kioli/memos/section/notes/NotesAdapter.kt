package kioli.memos.section.notes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kioli.memos.R
import kioli.memos.db.Note
import kioli.memos.db.NoteDataSource
import kotlinx.android.synthetic.main.item_note.view.*
import kotlinx.android.synthetic.main.item_note_on_delete.view.*
import java.util.*

class NotesAdapter(data: ArrayList<Note>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    private var items: ArrayList<Note> = data

    var clickOnUndo = { note: Note ->
        note.onDeletion = false
        notifyDataSetChanged()
    }

    var clickOnDelete = { context: Context, position: Int ->
        val note = items.removeAt(position)
        NoteDataSource.delete(context, note.id)
        notifyItemRemoved(position)

    }

    class ViewItem(noteView: View) : RecyclerView.ViewHolder(noteView) {
        fun bind(item: Note) = with(itemView) {
            note_text.text = item.note
        }
    }

    class ViewRemove(noteView: View) : RecyclerView.ViewHolder(noteView) {
        fun bind(item: Note, position: Int, listenerForDelete: (Context, Int) -> Unit, listenerForUndo: (Note) -> Unit) = with(itemView) {
            note_delete.setOnClickListener { listenerForDelete(context, position) }
            note_undo.setOnClickListener { listenerForUndo(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> return ViewItem(LayoutInflater.from(parent?.context).inflate(R.layout.item_note, parent, false))
            else -> return ViewRemove(LayoutInflater.from(parent?.context).inflate(R.layout.item_note_on_delete, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> (holder as ViewItem).bind(items[position])
            else -> (holder as ViewRemove).bind(items[position], position, clickOnDelete, clickOnUndo)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position].onDeletion) {
            return 1
        }
        return 0
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (items[fromPosition].onDeletion || items[toPosition].onDeletion) {
            return
        }
        when {
            fromPosition < toPosition -> {
                for (i in fromPosition..toPosition - 1) {
                    Collections.swap(items, i, i + 1)
                }
            }
            else -> {
                for (i in fromPosition downTo toPosition + 1) {
                    Collections.swap(items, i, i - 1)
                }
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        items[position].onDeletion = true
        notifyDataSetChanged()
    }
}