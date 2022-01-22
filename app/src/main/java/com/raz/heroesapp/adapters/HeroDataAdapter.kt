package com.raz.heroesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raz.heroesapp.databinding.HeroDetailCellBinding
import com.raz.heroesapp.template_models.HeroCategoryData

class HeroDataAdapter(private val items: List<HeroCategoryData>) :
    RecyclerView.Adapter<HeroDataAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val heroesListBinding = HeroDetailCellBinding.inflate(inflater, parent, false)
        return DataViewHolder(heroesListBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class DataViewHolder(private val binding: HeroDetailCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(category: HeroCategoryData) {
            binding.dataItem = category
        }
    }
}