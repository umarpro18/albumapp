package com.example.album.domain

import com.example.album.data.entities.Album
import com.example.album.domain.repository.AlbumRepository
import javax.inject.Inject

class InsertAlbumListLocalUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(albums: List<Album>) = albumRepository.insertAlbums(albums)
}
