package com.labs.orangestudy.data.repository

import com.labs.orangestudy.data.api.TvApi
import com.labs.orangestudy.data.model.Tv
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailRepository @Inject constructor (private val tvApi: TvApi) {
    suspend fun getTvById(tvId: Int): Tv? {
        val request = tvApi.getTvById(tvId)
        return request.body()
    }
}
