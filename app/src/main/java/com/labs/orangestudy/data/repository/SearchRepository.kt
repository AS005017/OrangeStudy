package com.labs.orangestudy.data.repository

import androidx.lifecycle.MutableLiveData
import com.labs.orangestudy.data.api.TvApi
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.paging.TvDataStorageSource
import com.labs.orangestudy.utils.NetworkState
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor (private val tvApi: TvApi) {

    private val localTvStorageSource: TvDataStorageSource.LocalTvStorage
        get() {
            return TvDataStorageSource.LocalTvStorage()
        }

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    var _tvNameLiveData = MutableLiveData<String>()
    var refreshState = MutableLiveData<Boolean>()


    suspend fun getTvByName(): List<Tv> {
        //val request = apiHelper.getTvById(tvId)
        networkState.value = NetworkState.LOADING
        val search = localTvStorageSource.findTvAsync(_tvNameLiveData.value.toString())
        var result: List<Tv>
        if (search.size == 0 || refreshState.value == true) {
            val request = tvApi.getTvByName(_tvNameLiveData.value.toString())
            if (request.isSuccessful){
                result = request.body()!!.results
                networkState.value = NetworkState.LOADED
            } else {
                result = emptyList()
                networkState.value = NetworkState.ERROR
            }
            refreshState.value = false
        } else {
            result = search.toList()
            networkState.value = NetworkState.LOADED
        }
        return result
    }
}
