package com.raz.heroesapp.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raz.heroesapp.server_models.Hero
import com.raz.heroesapp.server_models.HeroesSearchResults
import com.raz.heroesapp.server.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeroViewModel : ViewModel() {

    val getHeroResultsByNameResponse: MutableLiveData<List<Hero>> = MutableLiveData()

    fun sendGetHeroById(
        heroId: String,
        requestDoneListener: RequestDoneListener?
    ) {
        viewModelScope.launch {
            try {
                val getHeroes = RetrofitInstance.getServerRequests.getHeroById(heroId)
                getHeroes.enqueue(object : Callback<Hero> {
                    override fun onResponse(call: Call<Hero>, response: Response<Hero>) {
                        if (response.isSuccessful && response.body() != null) {
                            requestDoneListener?.onDone(response.body())
                        }
                    }

                    override fun onFailure(call: Call<Hero>, t: Throwable) {
                        requestDoneListener?.onFailed()
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
                requestDoneListener?.onFailed()
            }
        }
    }

    fun sendGetHeroesResultsByName(
        query: String,
        requestDoneListener: RequestDoneListener?
    ) {
        viewModelScope.launch {
            try {
                val getHeroes = RetrofitInstance.getServerRequests.getHeroesByName(query)
                getHeroes.enqueue(object : Callback<HeroesSearchResults> {
                    override fun onResponse(
                        call: Call<HeroesSearchResults>,
                        response: Response<HeroesSearchResults>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            getHeroResultsByNameResponse.value = response.body()!!.heroes
                        }
                    }

                    override fun onFailure(call: Call<HeroesSearchResults>, t: Throwable) {
                        requestDoneListener?.onFailed()
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
                requestDoneListener?.onFailed()
            }
        }
    }

    interface RequestDoneListener {
        fun onDone(hero: Hero?)
        fun onFailed()
    }
}