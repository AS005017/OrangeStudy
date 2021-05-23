package com.labs.orangestudy.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

open class Season(
    @SerializedName("air_date") var airDate: String = "",
    @SerializedName("episode_count") var episodeCount: Int = 0,
    @PrimaryKey
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("overview") var overview: String = "",
    @SerializedName("poster_path") var posterPath: String? = "",
    @SerializedName("season_number") var seasonNumber: Int = 0,
    @LinkingObjects("seasons") val owners: RealmResults<Tv>? = null
) : RealmObject()