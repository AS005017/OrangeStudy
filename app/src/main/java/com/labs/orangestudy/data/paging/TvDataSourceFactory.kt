package com.labs.orangestudy.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.repository.TvRepository
import kotlinx.coroutines.CoroutineScope

class TvDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: TvRepository
): DataSource.Factory<Int, Tv>() {

    val tvLiveDataSource =  MutableLiveData<TvDataSource>()

    override fun create(): DataSource<Int, Tv> {
        val tvDataSource =TvDataSource(coroutineScope,repository)
        tvLiveDataSource.postValue(tvDataSource)
        return tvDataSource
    }
}