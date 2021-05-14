package com.labs.orangestudy.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.labs.orangestudy.R
import com.labs.orangestudy.data.api.TvApi
import com.labs.orangestudy.data.model.Season
import com.labs.orangestudy.databinding.ItemSeasonBinding
import com.labs.orangestudy.databinding.ItemTvBinding
import com.squareup.picasso.Picasso

class SeasonsAdapter (private val seasons: List<Season>) :
    RecyclerView.Adapter<SeasonsAdapter.ViewHolder>() {

    private var _binding: ItemSeasonBinding? = null
    private val binding get() = _binding!!

    inner class ViewHolder(val binding: ItemSeasonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Season) {
            with(binding) {
                seasonTitle.text = item.name
                seasonEpisodes.text = item.episodeCount.toString()
                Picasso.get().load(TvApi.POSTER_BASE_URL+item.posterPath).placeholder(R.drawable.loading).into(seasonImg)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(seasons[position])

    override fun getItemCount(): Int =seasons.size

}