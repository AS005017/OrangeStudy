package com.labs.orangestudy.ui.main.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.labs.orangestudy.utils.NetworkState
import com.labs.orangestudy.data.paging.TvDataSourceFactory
import com.labs.orangestudy.data.api.TvApi
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.paging.TvBoundaryCallback
import com.labs.orangestudy.data.paging.TvDataSource
import com.labs.orangestudy.data.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor  (private val repository: TvRepository) : ViewModel() {

    val  networkState : LiveData<NetworkState> by lazy {
        Transformations.switchMap<TvDataSource, NetworkState>(
            dataSourceFactory.tvLiveDataSource, TvDataSource::networkState)
    }

    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(TvApi.POST_PER_PAGE)
        .setPrefetchDistance(TvApi.PREFETCH_DISTANCE)
        .build()

    private val tvBoundaryCallback = TvBoundaryCallback(viewModelScope, repository)
    private val dataSourceFactory = TvDataSourceFactory(viewModelScope, repository)
    val tvPagedListLiveData: LiveData<PagedList<Tv>> =
        LivePagedListBuilder(dataSourceFactory,pageListConfig).setBoundaryCallback(tvBoundaryCallback).build()

    fun listIsEmpty(): Boolean {
        return tvPagedListLiveData.value?.isEmpty() ?: true
    }

}