package com.labs.orangestudy.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class Tv(
    @SerializedName("backdrop_path")
    var backdropPath: String? = "",
    @SerializedName("last_air_date")
    var lastAirDate: String = "",
    @Ignore
    @SerializedName("genres")
    var genres: List<Genre> = listOf(),
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("original_name")
    var originalName: String? = "",
    @SerializedName("number_of_episodes")
    var numberOfEpisodes: Int = 0,
    @SerializedName("number_of_seasons")
    var numberOfSeasons: Int = 0,
    @SerializedName("overview")
    var overview: String?= "",
    @SerializedName("popularity")
    var popularity: Double = 0.0,
    @SerializedName("poster_path")
    var posterPath: String? = "",
    @Ignore
    @SerializedName("seasons")
    var seasons: List<Season> = listOf(),
    @SerializedName("status")
    var status: String = "",
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    var voteCount: Int = 0,
    var favorite: Boolean = false
    ) : RealmObject()
