package com.raz.heroesapp.server_models

import com.google.gson.annotations.SerializedName

data class HeroAppearance(
    val gender: String?,
    val race: String?,
    @SerializedName("eye-color")
    val eyeColor: String?,
    @SerializedName("hair-color")
    val hairColor: String?,
)