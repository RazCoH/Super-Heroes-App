package com.raz.heroesapp.views.fragments

import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.raz.heroesapp.R
import com.raz.heroesapp.adapters.HeroDataAdapter
import com.raz.heroesapp.adapters.HeroesAdapter
import com.raz.heroesapp.databinding.HeroDetailsFragmentLayoutBinding
import com.raz.heroesapp.server_models.Hero
import com.raz.heroesapp.template_models.HeroCategoryData

class HeroDetailsFragment(
    private val hero: Hero,
    private val listeners: HeroDetailsFragmentListeners
) : BaseFragment() {

    private lateinit var ivArrowBack: ImageView
    private lateinit var fubShare: FloatingActionButton
    private lateinit var rvCategoriesData: RecyclerView
    private var categoriesList = ArrayList<HeroCategoryData>()

    override fun initViews(view: View) {
        fubShare = view.findViewById(R.id.fubShare)
        ivArrowBack = view.findViewById(R.id.ivArrowBack)
        rvCategoriesData = view.findViewById(R.id.rvCategoriesData)
        initHeroCategoriesData()
        initHeroDataAdapter()
    }

    override fun initListeners() {
        ivArrowBack.setOnClickListener {
            listeners.onBack()
        }
        fubShare.setOnClickListener {
            listeners.onShare()
        }
    }

    override fun initTexts() {

    }

    override fun getContent(): Int {
        return R.layout.hero_details_fragment_layout
    }

    private fun initHeroDataAdapter() {
        val adapter = HeroDataAdapter(categoriesList)
        rvCategoriesData.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCategoriesData.adapter = adapter
    }

    private fun initHeroCategoriesData() {
        categoriesList.add(getHeroBiography())
        categoriesList.add(getHeroPowerStats())
        categoriesList.add(getHeroAppearance())
        categoriesList.add(getHeroWork())
    }

    private fun getHeroBiography(): HeroCategoryData {
        return HeroCategoryData(
            context?.resources?.getString(R.string.hero_biography_title),
            context?.resources?.getString(R.string.hero_full_name_title),
            context?.resources?.getString(R.string.hero_first_appearance),
            context?.resources?.getString(R.string.hero_place_of_birth_title),
            context?.resources?.getString(R.string.hero_publisher_title),
            hero.biography?.fullName,
            hero.biography?.firstAppearance,
            hero.biography?.placeOfBirth,
            hero.biography?.publisher,
        )
    }

    private fun getHeroPowerStats(): HeroCategoryData {
        return HeroCategoryData(
            context?.resources?.getString(R.string.hero_power_stats_title),
            context?.resources?.getString(R.string.hero_combat_title),
            context?.resources?.getString(R.string.hero_speed_title),
            context?.resources?.getString(R.string.hero_intelligence_title),
            context?.resources?.getString(R.string.hero_strength_title),
            hero.powerStats?.combat,
            hero.powerStats?.speed,
            hero.powerStats?.intelligence,
            hero.powerStats?.strength,
        )
    }

    private fun getHeroAppearance(): HeroCategoryData {
        return HeroCategoryData(
            context?.resources?.getString(R.string.hero_appearance_title),
            context?.resources?.getString(R.string.hero_gender_title),
            context?.resources?.getString(R.string.hero_race_title),
            context?.resources?.getString(R.string.hero_hair_color),
            context?.resources?.getString(R.string.hero_eye_color),
            hero.appearance?.gender,
            hero.appearance?.race,
            hero.appearance?.hairColor,
            hero.appearance?.eyeColor,
        )
    }

    private fun getHeroWork(): HeroCategoryData {
        return HeroCategoryData(
            context?.resources?.getString(R.string.hero_work_title),
            context?.resources?.getString(R.string.hero_base_title),
            context?.resources?.getString(R.string.hero_occupation_title),
            null,
            null,
            hero.work?.base,
            hero.work?.occupation,
            null,
            null,
        )
    }

    interface HeroDetailsFragmentListeners {
        fun onBack()
        fun onShare()
    }
}