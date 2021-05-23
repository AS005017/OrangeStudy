package com.labs.orangestudy.data.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class TvResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: ArrayList<Tv>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)