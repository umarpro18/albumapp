package com.example.album.domain

import com.example.album.data.model.GetAlbumListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Interface class responsible to make api call via retrofit
 */

interface AlbumService {

    @GET
    suspend fun getAlbumList(@Url albumListUrl: String): Response<GetAlbumListDto>

}
