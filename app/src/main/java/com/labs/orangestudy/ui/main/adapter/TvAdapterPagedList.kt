package com.labs.orangestudy.ui.main.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.*
import com.labs.orangestudy.R
import com.labs.orangestudy.utils.NetworkState
import com.labs.orangestudy.data.api.TvApi
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.databinding.ItemTvBinding
import com.magora.realm.ui.BaseRealmListenableAdapter
import com.magora.realmpaginator.RealmPagedList
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.Realm.init
import io.realm.RealmList

class TvAdapterPagedList(data: RealmPagedList<*,Tv>): BaseRealmListenableAdapter<Tv,RecyclerView.ViewHolder>(data) {

    var onItemClick: ((Tv) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TvItemViewHolder(ItemTvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as TvItemViewHolder).bindPost(it) }
    }

    inner class TvItemViewHolder (private val binding: ItemTvBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindPost(tv: Tv) {
                binding.tvTitle.text = tv.name
                binding.tvRate.text = tv.voteAverage.toString()
                Picasso.get()
                    .load(TvApi.POSTER_BASE_URL + tv.posterPath)
                    .placeholder(R.drawable.loading)
                    .into(binding.tvImg)
                itemView.setOnClickListener {
                    onItemClick?.invoke(tv)
                }


       }
    }

}