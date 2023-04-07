package com.example.album.data.entities

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

/**
 * A entity data class responsible storing/creating data in a table.
 */

@Parcelize
@Entity(tableName = "album", primaryKeys = ["name", "title"])
@Keep
data class Album(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "imageUrl60") val imageUrl60: String? = null,
    @ColumnInfo(name = "imageUrl170") val imageUrl170: String? = null,
    @ColumnInfo(name = "itemCount") val itemCount: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: String
) : Parcelable

