package com.labs.orangestudy.data.api

import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.model.TvResponse
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class ApiHelper @Inject constructor(private val api : TvApi) {
    suspend fun getTvById(tvId: Int) : Response<Tv> {
        return  api.getTvById(tvId)
    }

    suspend fun getPopularTv(pageIndex: Int): Response<TvResponse> {
        return api.getPopularTv(pageIndex)
    }
}