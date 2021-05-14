package com.labs.orangestudy.data.repository

import com.labs.orangestudy.data.api.TvApi
import com.labs.orangestudy.data.model.TvResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvRepository @Inject constructor (private val apiHelper: TvApi) {
    suspend fun getTvList(pageIndex: Int): TvResponse? {
        val request = apiHelper.getPopularTv(pageIndex)

        if (!request.isSuccessful) {
            return null
        }

        return request.body()
    }
}