package com.example.album.ui.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.album.R
import com.example.album.data.entities.Album
import com.example.album.databinding.ItemAlbumListBinding
import java.util.*
import kotlin.collections.ArrayList

/**
* A adapter class responsible for binding date into the view for list of albums
*/

class AlbumListAdapter(private val onClickListener: (Album) -> Unit) :
    RecyclerView.Adapter<AlbumViewHolder>(), Filterable {

    private val items = ArrayList<Album>()
    private val fullItems = ArrayList<Album>()

    fun setItems(items: ArrayList<Album>) {
        this.items.clear()
        this.items.addAll(items)

        this.fullItems.clear()
        this.fullItems.addAll(items)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding: ItemAlbumListBinding =
            ItemAlbumListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) =
        holder.bind(items[position], onClickListener)

    override fun getFilter(): Filter {
        return albumFilter
    }

    private val albumFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredAlbum = ArrayList<Album>()

            if (constraint.isNullOrBlank()) {
                filteredAlbum.addAll(fullItems)
            } else {
                val filteredPattern = constraint.toString().lowercase(Locale.ROOT).trim()
                for (item in fullItems) {
                    if (item.name.lowercase(Locale.ROOT).contains(filteredPattern)) {
                        filteredAlbum.add(item)
                    }
                }
            }

            val finalResult = FilterResults()
            finalResult.values = filteredAlbum
            return finalResult
        }

        override fun publishResults(constraint: CharSequence?, result: FilterResults?) {
            result?.let {
                items.clear()
                items.addAll(it.values as ArrayList<Album>)
                notifyDataSetChanged()
            }
        }
    }
}

class AlbumViewHolder(private val itemBinding: ItemAlbumListBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: Album, onClickListener: (Album) -> Unit) {
        itemBinding.textViewAlbumName.text = item.name
        itemBinding.textViewReleaseDate.text = item.releaseDate
        Glide.with(itemBinding.root)
            .load(item.imageUrl60)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(itemBinding.imageViewAlbum)

        itemBinding.root.setOnClickListener { onClickListener(item) }
    }

}
