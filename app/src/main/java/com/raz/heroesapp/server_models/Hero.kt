package com.raz.heroesapp.server_models

import com.google.gson.annotations.SerializedName

data class Hero(
    val id: String?,
    val name: String?,
    val biography: HeroBiography?,
    val image: HeroImage?,
    val work:HeroWork?,
    val appearance:HeroAppearance?,
    @SerializedName("powerstats")
    val powerStats:HeroPowerStats?,

)