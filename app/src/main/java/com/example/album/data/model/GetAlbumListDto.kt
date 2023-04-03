package com.example.album.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * A simple entity class responsible storing album data.
 */

@Keep
data class GetAlbumListDto(
    @SerializedName("feed") val feed: Feed?
) {
    data class Feed(@SerializedName("entry") val albums: List<AlbumDto>?)

    data class AlbumDto(
        @SerializedName("im:name") val name: Data?,
        @SerializedName("im:image") val imageUrlList: List<Data>?,
        @SerializedName("im:itemCount") val itemCount: Data?,
        @SerializedName("im:price") val price: Data?,
        @SerializedName("title") val title: Data?,
        @SerializedName("im:artist") val artist: Data?,
        @SerializedName("category") val category: AttributeData?,
        @SerializedName("im:releaseDate") val releaseDate: AttributeData?
    ) {
        data class Data(@SerializedName("label") val label: String?)

        data class AttributeData(@SerializedName("attributes") val attributes: Data?)
    }
}