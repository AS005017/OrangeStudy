package com.labs.orangestudy.data.api

import com.labs.orangestudy.BuildConfig
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.model.TvResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = BuildConfig.MOVIEDB_API_KEY
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        const val FIRST_PAGE = 1
        const val POST_PER_PAGE = 20
        const val PREFETCH_DISTANCE = 2
    }

    @GET("tv/popular?api_key=$API_KEY")
    suspend fun getPopularTv(@Query("page") page : Int) : Response<TvResponse>

    @GET("tv/{tv-id}?api_key=$API_KEY")
    suspend fun getTvById(@Path("tv-id") tvId: Int): Response<Tv>
}