package com.labs.orangestudy.data.paging

import androidx.lifecycle.MutableLiveData
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.repository.TvRepository
import com.magora.realmpaginator.RealmDataSource
import kotlinx.coroutines.CoroutineScope

class TvDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: TvRepository,
    private val localStorageSource: TvDataStorageSource.LocalTvStorage
): RealmDataSource.Factory<Int, Tv>() {

    val tvLiveDataSource =  MutableLiveData<TvDataSource>()

    override fun create(): RealmDataSource<Int, Tv> {
        val tvDataSource =TvDataSource(coroutineScope,repository, localStorageSource)
        tvLiveDataSource.postValue(tvDataSource)
        return tvDataSource
    }
}