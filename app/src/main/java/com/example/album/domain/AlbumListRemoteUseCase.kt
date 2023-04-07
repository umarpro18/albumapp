package com.example.album.domain

import androidx.fragment.app.Fragment
import com.example.album.domain.repository.AlbumRepository
import javax.inject.Inject

/**
 * Local use case class invoked from the view model to fetch data from remote source
 */

class AlbumListRemoteUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(albumListUrl: String) = albumRepository.getAlbumList(albumListUrl)
}
