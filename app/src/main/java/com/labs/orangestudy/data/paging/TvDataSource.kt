package com.labs.orangestudy.data.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.labs.orangestudy.utils.NetworkState
import com.labs.orangestudy.data.api.TvApi
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.repository.TvRepository
import com.magora.realmpaginator.RealmPageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TvDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: TvRepository,
    private val localStorageSource: TvDataStorageSource.LocalTvStorage
): RealmPageKeyedDataSource<Int, Tv>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Tv>
    ) {
        networkState.postValue(NetworkState.LOADING)
        coroutineScope.launch {
            val dbCheck = localStorageSource.getTvs()
            if (dbCheck.tvs.size == 0) {
                val response = repository.getTvList(params.initialKey!!)
                if (response == null) {
                networkState.postValue(NetworkState.ERROR)
                    return@launch
                }
                localStorageSource.putTvs(response.results, response.page)
                callback.onResult(response.results.size, null, response.page + 1)
            } else {
                callback.onResult(dbCheck.tvs.size, null, dbCheck.page + 1)
            }
            networkState.postValue(NetworkState.LOADED)
        }
    }


    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Tv>
    ) {
        networkState.postValue(NetworkState.LOADING)
        coroutineScope.launch {
            val response = repository.getTvList(params.key)
            if (response == null) {
                networkState.postValue(NetworkState.ERROR)
                return@launch
            }
            if (params.key <= response.totalPages) {
                localStorageSource.putTvsNextPage(response.results, response.page)
                callback.onResult(response.results.size, params.key + 1)
                networkState.postValue(NetworkState.LOADED)
            } else {
                networkState.postValue(NetworkState.ENDOFLIST)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Tv>) {

    }

}