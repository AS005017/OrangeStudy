package com.labs.orangestudy.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

open class Genre(
    @PrimaryKey
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = "",
    @LinkingObjects("genres") val owners: RealmResults<Tv>? = null
) : RealmObject()