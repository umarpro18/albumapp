package com.example.album.ui.albumdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.album.R
import com.example.album.data.entities.Album
import com.example.album.databinding.ItemSimilarCategoryAlbumListBinding

class SimilarCategoryAlbumListAdapter(private val onClickListener: (Album) -> Unit) :
    RecyclerView.Adapter<AlbumViewHolder>() {

    private val items = ArrayList<Album>()

    fun setItems(items: ArrayList<Album>) {
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding: ItemSimilarCategoryAlbumListBinding =
            ItemSimilarCategoryAlbumListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return AlbumViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) =
        holder.bind(items[position], onClickListener)

}

class AlbumViewHolder(private val itemBinding: ItemSimilarCategoryAlbumListBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: Album, onClickListener: (Album) -> Unit) {
        Glide.with(itemBinding.root)
            .load(item.imageUrl60)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(itemBinding.imageViewAlbum)

        itemBinding.root.setOnClickListener { onClickListener(item) }
    }

}
