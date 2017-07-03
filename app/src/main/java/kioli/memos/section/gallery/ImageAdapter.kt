package kioli.memos.section.gallery

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kioli.memos.R
import java.util.*

class ImageAdapter(data: ArrayList<GalleryItem>, val listener: (GalleryItem) -> Unit) : RecyclerView.Adapter<ImageAdapter.ViewImage>() {

    private var items: ArrayList<GalleryItem> = data

    class ViewImage(noteView: View) : RecyclerView.ViewHolder(noteView) {
        fun bind(item: GalleryItem, listenerForDelete: (GalleryItem) -> Unit) = with(itemView) {
//            Picasso.with(context).load(item.imagePath).into(gallery_image)
            setOnClickListener { listenerForDelete(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewImage {
        return ViewImage(LayoutInflater.from(parent?.context).inflate(R.layout.item_gallery, parent, false))
    }

    override fun onBindViewHolder(holder: ViewImage, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}