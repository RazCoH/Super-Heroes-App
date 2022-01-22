package com.raz.heroesapp.server_models

import com.google.gson.annotations.SerializedName


data class HeroBiography(
    @SerializedName("full-name")
    val fullName: String?,
    @SerializedName("place-of-birth")
    val placeOfBirth: String?,
    @SerializedName("first-appearance")
    val firstAppearance: String?,
    val publisher: String?,
    val alignment: String?,
    val aliases:List<String>?
)
