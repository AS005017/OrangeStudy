package com.labs.orangestudy.ui.main.viewmodel

import androidx.lifecycle.*
import com.labs.orangestudy.utils.NetworkState
import com.labs.orangestudy.data.paging.TvDataSourceFactory
import com.labs.orangestudy.data.api.TvApi
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.paging.TvDataSource
import com.labs.orangestudy.data.paging.TvDataStorageSource
import com.labs.orangestudy.data.repository.TvRepository
import com.magora.realmpaginator.RealmPagedList
import com.magora.realmpaginator.RealmPagedListBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor  (private val repository: TvRepository) : ViewModel() {

    private val localTvStorageSource: TvDataStorageSource.LocalTvStorage
        get() {
            return TvDataStorageSource.LocalTvStorage()
        }

//    val  networkState : LiveData<NetworkState> by lazy {
//        Transformations.switchMap<TvDataSource, NetworkState>(
//            dataSourceFactory.tvLiveDataSource, TvDataSource::networkState)
//    }

    private val pageListConfig: RealmPagedList.Config = RealmPagedList.Config.Builder()
        .setPageSize(TvApi.POST_PER_PAGE)
        .setPrefetchDistance(TvApi.PREFETCH_DISTANCE)
        .build()

   // private val tvBoundaryCallback = TvBoundaryCallback(viewModelScope, repository)
    private val dataSourceFactory = TvDataSourceFactory(viewModelScope, repository, localTvStorageSource)
    val tvPagedListLiveData: RealmPagedList<Int,Tv> =
        RealmPagedListBuilder(dataSourceFactory,pageListConfig).setInitialLoadKey(1).setRealmData(localTvStorageSource.getTvs().tvs).build()

//    fun listIsEmpty(): Boolean {
//        return tvPagedListLiveData.value?.isEmpty() ?: true
//    }

}