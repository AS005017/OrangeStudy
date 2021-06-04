package com.labs.orangestudy.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.labs.orangestudy.R
import com.labs.orangestudy.data.api.TvApi
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.databinding.ItemSeasonBinding
import com.labs.orangestudy.databinding.ItemTvBinding
import com.squareup.picasso.Picasso

class SearchAdapter  (private val tvs: List<Tv>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var onItemClick: ((Tv) -> Unit)? = null

        inner class ViewHolder(val binding: ItemTvBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Tv) {
                with(binding) {
                    tvTitle.text = item.name
                    tvRate.text = item.voteAverage.toString()
                    Picasso.get().load(TvApi.POSTER_BASE_URL+item.posterPath).placeholder(R.drawable.loading).into(tvImg)
                    itemView.setOnClickListener {
                        onItemClick?.invoke(item)
                    }
                }
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemTvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(tvs[position])

        override fun getItemCount(): Int =tvs.size

    }