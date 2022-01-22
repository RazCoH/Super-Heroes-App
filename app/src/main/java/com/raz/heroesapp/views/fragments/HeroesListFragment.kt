package com.raz.heroesapp.views.fragments

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raz.heroesapp.R
import com.raz.heroesapp.adapters.HeroesAdapter
import com.raz.heroesapp.server_models.Hero
import com.raz.heroesapp.utils.Constants.Companion.BLACK_PANTHER_ID
import com.raz.heroesapp.utils.Constants.Companion.THOR_ID
import com.raz.heroesapp.utils.Constants.Companion.WONDER_WOMAN
import com.raz.heroesapp.view_models.HeroViewModel
import com.raz.heroesapp.view_models.ViewModelFactory

class HeroesListFragment(private val listeners: HeroesListFragmentListeners?) : BaseFragment() {

    private val viewModelFactory = ViewModelFactory()
    private lateinit var heroViewModel: HeroViewModel
    private lateinit var etSearch: EditText
    private lateinit var rvHeroes: RecyclerView
    private var heroesList = ArrayList<Hero>()
    private lateinit var vProgress: ProgressBar
    private lateinit var tvNoResults: TextView
    private var firstLoadedHeroesIds = arrayOf(BLACK_PANTHER_ID, THOR_ID, WONDER_WOMAN)

    override fun initViews(view: View) {
        etSearch = view.findViewById(R.id.etSearch)
        rvHeroes = view.findViewById(R.id.rvHeroes)
        vProgress = view.findViewById(R.id.vProgress)
        tvNoResults = view.findViewById(R.id.tvNoResults)
        heroViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HeroViewModel::class.java]
        initFirstHeroesList()
    }

    override fun initListeners() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null && p0.length > 2) {
                    searchLogic(p0.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchLogic(text: String) {
        showLoader()
        heroViewModel.sendGetHeroesResultsByName(text, object : HeroViewModel.RequestDoneListener {
            override fun onDone(hero: Hero?) {

            }

            override fun onFailed() {
                Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show()
            }

        })
        heroViewModel.getHeroResultsByNameResponse.observe(requireActivity(), {
            if (it != null) {
                tvNoResults.visibility = View.GONE
                heroesList.clear()
                heroesList.addAll(ArrayList(it))
                rvHeroes.adapter?.notifyDataSetChanged()
                dataUpdateLogic()
                etSearch.clearFocus()
            } else {
                hideLoader()
                rvHeroes.visibility = View.GONE
                tvNoResults.visibility = View.VISIBLE

            }
        })

    }

    override fun initTexts() {

    }

    private fun initFirstHeroesList() {
        if (heroesList.isEmpty()) {
            showDefaultHeroes()
        }else{
            dataUpdateLogic()
        }
    }

    private fun showDefaultHeroes(){
        showLoader()
        for (heroId in firstLoadedHeroesIds) {
            heroViewModel.sendGetHeroById(
                heroId,
                object : HeroViewModel.RequestDoneListener {
                    override fun onDone(hero: Hero?) {
                        if (hero != null) {
                            heroesList.add(hero)
                        }
                        if (heroesList.size == firstLoadedHeroesIds.size) {
                            dataUpdateLogic()
                        }
                    }

                    override fun onFailed() {
                        Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        }
    }

    private fun showLoader() {
        tvNoResults.visibility = View.GONE
        vProgress.visibility = View.VISIBLE
        rvHeroes.visibility = View.GONE
    }

    private fun hideLoader() {
        rvHeroes.visibility = View.VISIBLE
        vProgress.visibility = View.GONE
    }

    private fun dataUpdateLogic() {
        hideLoader()
        initHeroesAdapter()
    }

    private fun initHeroesAdapter() {
        val adapter = HeroesAdapter(heroesList, object : HeroesAdapter.OnHeroSelectedListener {
            override fun onHeroSelected(item: Hero) {
                listeners?.onHeroTapped(item)
            }
        })
        rvHeroes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvHeroes.adapter = adapter
    }

    override fun getContent(): Int {
        return R.layout.heroes_list_fragment_layout
    }

    interface HeroesListFragmentListeners {
        fun onHeroTapped(hero: Hero)
    }
}