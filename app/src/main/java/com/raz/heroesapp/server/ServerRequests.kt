package com.raz.heroesapp.server

import com.raz.heroesapp.server_models.Hero
import com.raz.heroesapp.server_models.HeroesSearchResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ServerRequests {

    @GET("{HERO_ID}")
    fun getHeroById(@Path("HERO_ID") id: String): Call<Hero>

    @GET("search/{HERO_NAME}")
    fun getHeroesByName(@Path("HERO_NAME") query: String): Call<HeroesSearchResults>
}