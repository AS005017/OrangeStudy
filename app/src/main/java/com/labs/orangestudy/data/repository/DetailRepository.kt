package com.labs.orangestudy.data.repository

import com.labs.orangestudy.data.api.ApiHelper
import com.labs.orangestudy.data.model.Tv
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailRepository @Inject constructor (private val apiHelper: ApiHelper) {
    suspend fun getTvById(tvId: Int): Tv? {
        val request = apiHelper.getTvById(tvId)

        if (request.failed || !request.isSuccessful)
            return null

        return request.body
    }
}
