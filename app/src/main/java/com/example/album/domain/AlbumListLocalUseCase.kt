package com.example.album.domain

import com.example.album.domain.repository.AlbumRepository
import javax.inject.Inject

/**
 * Local use case class invoked from the view model to fetch data from local source
 */

class AlbumListLocalUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(title: String, categoryName: String) =
        albumRepository.getAlbumListBasedOnSimilarCategory(title, categoryName)
}
