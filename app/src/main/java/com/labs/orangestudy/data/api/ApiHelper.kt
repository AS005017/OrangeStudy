package com.labs.orangestudy.data.api

import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.model.TvResponse
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class ApiHelper @Inject constructor(private val api : TvApi) {
    suspend fun getTvById(tvId: Int) : SimpleResponse<Tv> {
        return safeApiCall { api.getTvById(tvId) }
    }

    suspend fun getPopularTv(pageIndex: Int): SimpleResponse<TvResponse> {
        return safeApiCall { api.getPopularTv(pageIndex) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (exception: Exception) {
            SimpleResponse.failure(exception)
        }
    }
}