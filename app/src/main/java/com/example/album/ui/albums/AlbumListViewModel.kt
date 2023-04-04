package com.example.album.ui.albums

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.album.data.model.AlbumList
import com.example.album.domain.AlbumListRemoteUseCase
import com.example.album.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_URL = "limit=100/json"

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val albumListRemoteUseCase: AlbumListRemoteUseCase
) : ViewModel() {

    val albumListViewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        fetchAlbumList()
    }

    fun fetchAlbumList() {
        viewModelScope.launch {
            try {
                albumListViewState.value = ViewState.Loading
                when (val albumListResponse = getAlbumList(PAGE_URL)) {
                    is Result.Success -> {
                        albumListViewState.value = ViewState.AlbumListLoaded(albumListResponse.data)
                    }
                    is Result.Error -> {
                        albumListViewState.value =
                            ViewState.AlbumListLoadFailure("Network or other technical issue, please try again later!")
                    }
                }
            } catch (ex: Exception) {
                albumListViewState.value = ViewState.AlbumListLoadFailure()
            }
        }
    }

    private suspend fun getAlbumList(url: String): Result<AlbumList> {
        return albumListRemoteUseCase.invoke(url)
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class AlbumListLoaded(val albums: AlbumList) : ViewState()
        data class AlbumListLoadFailure(val errorMessage: String? = null) : ViewState()
    }
}