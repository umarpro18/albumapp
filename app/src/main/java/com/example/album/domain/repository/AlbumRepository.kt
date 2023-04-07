package com.example.album.domain.repository

import com.example.album.data.db.GetLocalAlbumListDataSource
import com.example.album.data.entities.Album
import com.example.album.data.model.AlbumList
import com.example.album.data.remote.GetRemoteAlbumListDataSource
import com.example.album.utils.CoroutineDispatcherProvider
import com.example.album.utils.Result
import kotlinx.coroutines.withContext

/**
 * Repository class responsible to fetch data either from local or remote
 * It uses background thread to run the job
 */

class AlbumRepository(
    private val getRemoteAlbumListDataSource: GetRemoteAlbumListDataSource,
    private val getLocalAlbumListDataSource: GetLocalAlbumListDataSource,
    private val dispatcherProvider: CoroutineDispatcherProvider
) {
    suspend fun getAlbumList(albumListUrl: String): Result<AlbumList> =
        withContext(dispatcherProvider.io) {
            getRemoteAlbumListDataSource.getAlbumList(albumListUrl)
        }

    suspend fun getAlbumListBasedOnSimilarCategory(title: String, categoryName: String): List<Album>? =
        withContext(dispatcherProvider.io) {
            getLocalAlbumListDataSource.getAlbumListBasedOnSimilarCategory(title, categoryName)
        }

    suspend fun insertAlbums(albums: List<Album>) =
        withContext(dispatcherProvider.io) {
            getLocalAlbumListDataSource.insertAll(albums)
        }
}
