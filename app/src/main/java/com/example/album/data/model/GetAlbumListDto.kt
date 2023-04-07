package com.example.album.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * A simple dto date class converts api call response into data object
 */

@Keep
@JsonClass(generateAdapter = true)
data class GetAlbumListDto(
    @Json(name = "feed") val feed: Feed?
) {
    @JsonClass(generateAdapter = true)
    data class Feed(@Json(name = "entry") val albums: List<AlbumDto>?)

    @JsonClass(generateAdapter = true)
    data class AlbumDto(
        @Json(name = "im:name") val name: Data?,
        @Json(name = "im:image") val imageUrlList: List<Data>?,
        @Json(name = "im:itemCount") val itemCount: Data?,
        @Json(name = "im:price") val price: Data?,
        @Json(name = "title") val title: Data?,
        @Json(name = "im:artist") val artist: Data?,
        @Json(name = "category") val category: AttributeData?,
        @Json(name = "im:releaseDate") val releaseDate: AttributeData?
    ) {
        @JsonClass(generateAdapter = true)
        data class Data(@Json(name = "label") val label: String?)

        @JsonClass(generateAdapter = true)
        data class AttributeData(@Json(name = "attributes") val attributes: Data?)
    }
}