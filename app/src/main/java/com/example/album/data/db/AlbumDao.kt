package com.example.album.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.album.data.entities.Album

/**
 * A simple dao class responsible for the db operations such as
 * retrieve, insert album data.
 */

@Dao
interface AlbumDao {

    @Query("SELECT * FROM album WHERE title !=:title AND category =:categoryName")
    suspend fun getAlbumListBasedOnSimilarCategory(title: String, categoryName: String): List<Album>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)

}
