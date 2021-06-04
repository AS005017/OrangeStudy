package com.labs.orangestudy.data.repository

import com.labs.orangestudy.data.api.TvApi
import com.labs.orangestudy.data.model.TvResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvRepository @Inject constructor (private val tvApi: TvApi) {
    suspend fun getTvList(pageIndex: Int): TvResponse? {
        val request = tvApi.getPopularTv(pageIndex)
        return request.body()
    }
}