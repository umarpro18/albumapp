package com.example.album.domain

import com.example.album.data.entities.Album
import com.example.album.domain.repository.AlbumRepository
import javax.inject.Inject

/**
 * Local use case class invoked from the view model to insert data from remote source
 */

class InsertAlbumListLocalUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(albums: List<Album>) = albumRepository.insertAlbums(albums)
}
