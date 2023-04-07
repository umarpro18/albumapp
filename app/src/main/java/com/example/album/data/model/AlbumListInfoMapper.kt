package com.example.album.data.model

import com.example.album.data.entities.Album
import com.example.album.utils.Mapper
import javax.inject.Inject

/**
 * A simple mapper class responsible mapping album list dto into dao.
 */

class AlbumListInfoMapper @Inject constructor() : Mapper<GetAlbumListDto?, AlbumList> {
    override fun map(input: GetAlbumListDto?): AlbumList {
        val albumList = input?.feed?.albums?.map {
            val name = it.name?.label.orEmpty()
            val imageUrl60 = if (it.imageUrlList?.isNotEmpty() == true) it.imageUrlList.getOrNull(1)?.label.orEmpty() else null
            val imageUrl170 = if (it.imageUrlList?.isNotEmpty() == true) it.imageUrlList.getOrNull(2)?.label.orEmpty() else null
            val itemCount = it.itemCount?.label.orEmpty()
            val title = it.title?.label.orEmpty()
            val price = it.price?.label.orEmpty()
            val artist = it.artist?.label.orEmpty()
            val category = it.category?.attributes?.label.orEmpty()
            val releaseDate = it.releaseDate?.attributes?.label.orEmpty()
            Album(
                name = name,
                imageUrl60 = imageUrl60,
                imageUrl170 = imageUrl170,
                itemCount = itemCount,
                title = title,
                price = price,
                artist = artist,
                category = category,
                releaseDate = releaseDate
            )
        } ?: emptyList()
        return AlbumList(
            albumList = albumList
        )
    }
}