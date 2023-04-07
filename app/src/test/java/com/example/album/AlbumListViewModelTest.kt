package com.example.album

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.album.data.entities.Album
import com.example.album.data.model.AlbumList
import com.example.album.domain.AlbumListLocalUseCase
import com.example.album.domain.AlbumListRemoteUseCase
import com.example.album.domain.InsertAlbumListLocalUseCase
import com.example.album.domain.repository.AlbumRepository
import com.example.album.ui.albums.AlbumListViewModel
import com.example.album.util.MainCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

/**
 * Class responsible for unit tests
 */

@ExperimentalCoroutinesApi
class AlbumListViewModelTest {

    companion object {
        const val ALBUM_NAME = "FirstAlbum"
        const val PAGE_LINK = "ALBUM_LINK"
    }

    private lateinit var viewModel: AlbumListViewModel

    @MockK
    lateinit var albumListRemoteUseCase: AlbumListRemoteUseCase

    @MockK
    lateinit var albumListLocalUseCase: AlbumListLocalUseCase

    @MockK
    lateinit var insertAlbumListLocalUseCase: InsertAlbumListLocalUseCase

    @Rule
    @JvmField
    var instantTaskExecuteRole = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = createViewModel()
    }

    private val albumList = listOf(
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
        )
    )

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


    @Test
    fun testLoadAlbumListFromRemote() {
        val observer = createObserverForUserSettingsRequestState()

        val mockAlbumList = albumListResponse

        coEvery { albumListRemoteUseCase.invoke(any()) } returns com.example.album.utils.Result.Success(
            mockAlbumList
        )

        viewModel.fetchAlbumList()

        verifyThatAlbumListIsSuccessfullyLoaded(observer, mockAlbumList)
    }

    @Test
    fun testGetSimilarCategoryAlbumListFromLocal() {
        val repository: AlbumRepository = Mockito.mock(AlbumRepository::class.java)
        runBlocking {
            Mockito.`when`(repository.getAlbumListBasedOnSimilarCategory("title", "categoryFilter"))
                .thenReturn(albumList)

            AlbumListLocalUseCase(repository).invoke("title", "categoryFilter").let {
                assert(it is List<Album>)
                if (it != null) {
                    assert(it.isNotEmpty())
                    for (item in it) {
                        val name = item.name
                        assert(name == ALBUM_NAME)
                    }
                }
            }
        }
    }

    @Test
    fun testInsertAlbumList() {
        val observer = createObserverForUserSettingsRequestState()

        val mockAlbumList = albumListResponse

        coEvery { albumListRemoteUseCase.invoke(any()) } returns com.example.album.utils.Result.Success(
            mockAlbumList
        )

        viewModel.fetchAlbumList()

        verifyThatAlbumListIsSuccessfullyLoaded(observer, mockAlbumList)

        coVerify(exactly = 0) {
            insertAlbumListLocalUseCase.invoke(albumList)
        }
    }

    private fun verifyThatAlbumListIsSuccessfullyLoaded(
        observer: Observer<AlbumListViewModel.ViewState>,
        albumList: AlbumList
    ) {
        verifySequence {
            observer.onChanged(AlbumListViewModel.ViewState.Loading)
            observer.onChanged(AlbumListViewModel.ViewState.Loading)
            observer.onChanged(AlbumListViewModel.ViewState.AlbumListLoaded(albumList))
        }
    }

    private fun createObserverForUserSettingsRequestState(): Observer<AlbumListViewModel.ViewState> {
        val observer =
            mockk<Observer<AlbumListViewModel.ViewState>> { every { onChanged(any()) } just Runs }
        viewModel.albumListViewState.observeForever(observer)
        return observer
    }

    private fun createViewModel(): AlbumListViewModel {
        return AlbumListViewModel(
            albumListRemoteUseCase = albumListRemoteUseCase,
            insertAlbumListLocalUseCase = insertAlbumListLocalUseCase
        )
    }
}
