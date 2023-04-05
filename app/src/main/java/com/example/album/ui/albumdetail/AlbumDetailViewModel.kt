package com.example.album.ui.albumdetail

import androidx.lifecycle.*
import com.example.album.data.model.AlbumList
import com.example.album.domain.AlbumListLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val albumListLocalUseCase: AlbumListLocalUseCase
) : ViewModel() {

    private val _categoryFilter = MutableLiveData<Pair<String, String>>()

    private val _similarAlbumsBasedOnCategory = _categoryFilter.switchMap { filter ->
        liveData(viewModelScope.coroutineContext) {
            albumListLocalUseCase.invoke(filter.first, filter.second)?.let {
                emit(AlbumList(it))
            }
        }
    }

    val similarAlbumsBasedOnCategory: LiveData<AlbumList> = _similarAlbumsBasedOnCategory

    fun getSimilarAlbumsBasedOnCategory(title: String, categoryName: String) {
        _categoryFilter.postValue(Pair(title, categoryName))
    }
}