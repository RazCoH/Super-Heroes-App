package com.raz.heroesapp.server_models

import com.google.gson.annotations.SerializedName

data class HeroesSearchResults(@SerializedName("results") var heroes:List<Hero>?)