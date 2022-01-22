package com.raz.heroesapp.objects

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.FragmentActivity
import com.raz.heroesapp.R
import com.raz.heroesapp.server_models.Hero
import com.raz.heroesapp.views.activities.MainActivity
import com.raz.heroesapp.views.fragments.HeroDetailsFragment
import com.raz.heroesapp.views.fragments.HeroesListFragment
import java.net.URL

object FragmentsAndActivitiesNavigation {

    fun pushMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    fun pushHeroesListFragment(activity: FragmentActivity) {
        val heroesListFragment =
            HeroesListFragment(object : HeroesListFragment.HeroesListFragmentListeners {
                override fun onHeroTapped(hero: Hero) {
                    pushHeroDetailsFragment(hero, activity)
                }

            })
        activity.supportFragmentManager.beginTransaction()
            .addToBackStack(heroesListFragment.javaClass.name)
            .setCustomAnimations(R.anim.enter_fragment_animation, R.anim.exit_fragment_animation)
            .replace(R.id.fragments_container, heroesListFragment)
            .commit()
    }

    fun pushHeroDetailsFragment(hero: Hero, activity: FragmentActivity) {
        val heroDetailsFragment =
            HeroDetailsFragment(hero, object : HeroDetailsFragment.HeroDetailsFragmentListeners {
                override fun onBack() {
                    activity.supportFragmentManager.popBackStackImmediate()
                }

                override fun onShare() {
                    shareHeroDetails(activity, hero)
                }
            })
        activity.supportFragmentManager.beginTransaction()
            .addToBackStack(heroDetailsFragment.javaClass.name)
            .setCustomAnimations(R.anim.enter_fragment_animation, R.anim.exit_fragment_animation)
            .replace(R.id.fragments_container, heroDetailsFragment)
            .commit()
    }

    fun shareHeroDetails(activity: FragmentActivity, hero: Hero?) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/html"
        shareIntent.putExtra(Intent.EXTRA_TEXT, getTextForIntent(hero))
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        activity.startActivity(
            Intent.createChooser(
                shareIntent,
                activity.resources.getString(R.string.hero_share)
            )
        )
    }

    private fun getTextForIntent(hero: Hero?): String {
        return "Hay look that hero: \n" +
                "${hero?.name} (${hero?.biography?.fullName}). \n" +
                "born in ${hero?.biography?.placeOfBirth} and published by ${hero?.biography?.publisher}. \n" +
                "you can see his/her picture through this link:\n" +
                "\n ${hero?.image?.url}"
    }
}