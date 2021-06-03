package com.labs.orangestudy.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.paging.TvDataStorageSource
import com.labs.orangestudy.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class SearchViewModel: ViewModel() {

    private val _tvByNameLiveData = MutableLiveData<Tv>()
    val tvByNameLiveData: LiveData<Tv> = _tvByNameLiveData

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val localTvStorageSource: TvDataStorageSource.LocalTvStorage
        get() {
            return TvDataStorageSource.LocalTvStorage()
        }

    val searchQuery = MutableStateFlow("")

    suspend fun  search() {
        localTvStorageSource.findTvAsync(searchQuery.toString())
    }

}