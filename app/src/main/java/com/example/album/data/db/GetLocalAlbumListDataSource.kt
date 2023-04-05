package com.example.album.data.db

import com.example.album.data.entities.Album
import javax.inject.Inject

class GetLocalAlbumListDataSource @Inject constructor(private val albumDao: AlbumDao) {

    suspend fun getAlbumListBasedOnSimilarCategory(title: String, category: String) = albumDao.getAlbumListBasedOnSimilarCategory(title, category)

    suspend fun insertAll(albums: List<Album>) = albumDao.insertAll(albums)

}
