package com.labs.orangestudy.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.paging.TvDataStorageSource
import com.labs.orangestudy.data.repository.DetailRepository
import com.labs.orangestudy.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.RealmObject
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor  (private val repository: DetailRepository) : ViewModel() {


    private val _tvByIdLiveData = MutableLiveData<Tv>()
    val tvByIdLiveData: LiveData<Tv> = _tvByIdLiveData

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val localTvStorageSource: TvDataStorageSource.LocalTvStorage
        get() {
            return TvDataStorageSource.LocalTvStorage()
        }

    fun getTv(id:Int) {
        viewModelScope.launch{
            val tv = localTvStorageSource.getTvAsync(id)
            if (tv != null) {
                _tvByIdLiveData.postValue(RealmObject.getRealm(tv).copyFromRealm(tv))
                _networkState.postValue(NetworkState.LOADED)
            } else {
                _networkState.postValue(NetworkState.ERROR)
            }
        }
    }

    fun refreshTv (tvId: Int) {
        _networkState.postValue(NetworkState.LOADING)
        viewModelScope.launch {
            val response = repository.getTvById(tvId)

            if (response != null) {
                _tvByIdLiveData.postValue(response!!)
                _networkState.postValue(NetworkState.LOADED)
            } else {
                _networkState.postValue(NetworkState.ERROR)
            }
        }
    }
}