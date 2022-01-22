package com.raz.heroesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raz.heroesapp.databinding.HeroCellLayoutBinding
import com.raz.heroesapp.server_models.Hero

class HeroesAdapter(private val items: List<Hero>, private val listener: OnHeroSelectedListener) :
    RecyclerView.Adapter<HeroesAdapter.HeroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val heroesListBinding = HeroCellLayoutBinding.inflate(inflater, parent, false)
        return HeroViewHolder(heroesListBinding)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bindData(items[position])
        holder.itemView.setOnClickListener {
            listener.onHeroSelected(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class HeroViewHolder(private val binding: HeroCellLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(hero: Hero) {
            binding.heroItem = hero
        }
    }

    interface OnHeroSelectedListener {
        fun onHeroSelected(item: Hero)
    }
}