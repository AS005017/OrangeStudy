package com.labs.orangestudy.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.paging.TvDataStorageSource
import com.labs.orangestudy.data.repository.SearchRepository
import com.labs.orangestudy.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.RealmResults
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor  (private val repository: SearchRepository): ViewModel() {

    private val _tvByNameLiveData = MutableLiveData<List<Tv>>()
    val tvByNameLiveData: LiveData<List<Tv>> = _tvByNameLiveData

    //private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = repository.networkState


    fun search(query: String) {
    repository._tvNameLiveData.value = query
        viewModelScope.launch() {
            _tvByNameLiveData.postValue(repository.getTvByName())
        }
    }

    fun refresh() {
       repository.refreshState.value = true
        viewModelScope.launch() {
            _tvByNameLiveData.postValue(repository.getTvByName())
        }
    }

}