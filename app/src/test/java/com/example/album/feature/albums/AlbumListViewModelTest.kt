package com.example.album.feature.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.album.data.entities.Album
import com.example.album.data.model.AlbumList
import com.example.album.domain.AlbumListRemoteUseCase
import com.example.album.domain.repository.AlbumRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class AlbumListViewModelTest {

    companion object {
        private const val ALBUM_NAME = "FirstAlbum"
        private const val PAGE_LINK = "ALBUM_LINK"
    }

    @Rule
    @JvmField
    var instantTaskExecuteRole = InstantTaskExecutorRule()

    private val albumListResponse = AlbumList(
        listOf(
            Album(
                name = ALBUM_NAME,
                imageUrl60 = "image",
                imageUrl170 = "image",
                itemCount = "10",
                price = "$20",
                title = "AlbumRock",
                artist = "Luke",
                category = "POP",
                releaseDate = "March 23rd"
            ),
            Album(
                name = "General",
                imageUrl60 = "image2",
                imageUrl170 = "image2",
                itemCount = "14",
                price = "$30",
                title = "AlbumClassic",
                artist = "Jenni",
                category = "ROCK",
                releaseDate = "May 23rd"
            )
        )
    )

    private val emptyAlbumList = AlbumList(emptyList<Album>())

    @Test
    fun testGetSingleAlbumList() {
        val repository: AlbumRepository = Mockito.mock(AlbumRepository::class.java)
        runBlocking {
            Mockito.`when`(repository.getAlbumList(PAGE_LINK))
                .thenReturn(com.example.album.utils.Result.Success(albumListResponse))

            AlbumListRemoteUseCase(repository).invoke(PAGE_LINK).let {
                assert(it is com.example.album.utils.Result.Success<*>)
                when (it) {
                    is com.example.album.utils.Result.Success -> {
                        assert(it.data.albumList.isNotEmpty())
                        assert(it.data.albumList[0].name == ALBUM_NAME)
                    }
                    else -> {}
                }
            }
        }
    }

    @Test
    fun testGetEmptyAlbumList() {
        val repository: AlbumRepository = Mockito.mock(AlbumRepository::class.java)
        runBlocking {
            Mockito.`when`(repository.getAlbumList(PAGE_LINK))
                .thenReturn(com.example.album.utils.Result.Success(emptyAlbumList))

            AlbumListRemoteUseCase(repository).invoke(PAGE_LINK).let {
                assert(it is com.example.album.utils.Result.Success)
                when (it) {
                    is com.example.album.utils.Result.Success -> {
                        assert(it.data.albumList.isEmpty())
                    }
                    else -> {}
                }
            }
        }
    }
}
