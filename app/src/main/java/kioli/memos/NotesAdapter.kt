package kioli.memos

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_note.view.*

class NotesAdapter(data: ArrayList<String>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>()  {

    private var items: ArrayList<String> = data

    class ViewHolder(noteView: View) : RecyclerView.ViewHolder(noteView) {
        fun bind(item: String) = with(itemView) {
            note_text.text = item
        }
    }

//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(item: Item, listener: (Item) -> Unit) = with(itemView) {
//            itemTitle.text = item.title
//            itemImage.loadUrl(item.url)
//            setOnClickListener { listener(item) }
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}