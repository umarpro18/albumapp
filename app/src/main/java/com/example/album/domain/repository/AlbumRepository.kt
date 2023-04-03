package com.example.album.domain.repository

import com.example.album.data.model.AlbumList
import com.example.album.data.remote.GetRemoteAlbumListDataSource
import com.example.album.utils.CoroutineDispatcherProvider
import com.example.album.utils.Result
import kotlinx.coroutines.withContext

class AlbumRepository(
    private val getRemoteAlbumListDataSource: GetRemoteAlbumListDataSource,
    private val dispatcherProvider: CoroutineDispatcherProvider
) {
    suspend fun getAlbumList(albumListUrl: String): Result<AlbumList> =
        withContext(dispatcherProvider.io) {
            getRemoteAlbumListDataSource.getAlbumList(albumListUrl)
        }
}