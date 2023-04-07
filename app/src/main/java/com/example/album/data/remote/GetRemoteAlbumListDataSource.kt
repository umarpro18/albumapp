package com.example.album.data.remote

import com.example.album.data.model.AlbumList
import com.example.album.data.model.AlbumListInfoMapper
import com.example.album.domain.AlbumService
import com.example.album.utils.Result
import com.example.album.utils.safeApiCall
import java.io.IOException
import javax.inject.Inject

/**
 * A simple class responsible handle api response and sets success or failure objects
 */

class GetRemoteAlbumListDataSource
@Inject constructor(
    private val albumService: AlbumService,
    private val albumListMapper: AlbumListInfoMapper
) {

    suspend fun getAlbumList(albumListUrl: String) = safeApiCall(
        call = { requestAlbumList(albumListUrl) },
        errorMessage = "Error getting profiles, url:$albumListUrl"
    )

    private suspend fun requestAlbumList(
        albumListUrl: String
    ): Result<AlbumList> {
        val response = albumService.getAlbumList(albumListUrl)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                val albumList = albumListMapper.map(body)
                return Result.Success(albumList)
            }
        }
        return Result.Error(
            exception = IOException(
                response.errorBody()?.toString()
                    ?: "requestAlbumList() ${response.message()}"
            ),
            statusCode = response.code()
        )
    }
}
