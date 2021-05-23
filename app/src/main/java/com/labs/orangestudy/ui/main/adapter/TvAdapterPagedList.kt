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
import com.labs.orangestudy.databinding.ErrorStateBinding
import com.labs.orangestudy.databinding.FragmentTvBinding
import com.labs.orangestudy.databinding.ItemTvBinding
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.Realm.init

class TvAdapterPagedList(private val realm: Realm): PagedListAdapter<Tv,RecyclerView.ViewHolder>(TvDiffCallback()) {

    var onItemClick: ((Tv) -> Unit)? = null
//    private var _binding: ItemTvBinding? = null
//    private var __binding: ErrorStateBinding? = null
//    private val binding get() = _binding!!
//    private val bbinding get() = __binding!!
//    private var currSize: Int = 0

//    val MOVIE_VIEW_TYPE = 1
//    val NETWORK_VIEW_TYPE = 2
//
//    private var networkState: NetworkState? = null


    class TvDiffCallback: DiffUtil.ItemCallback<Tv>() {
        override fun areItemsTheSame(
            oldItem: Tv,
            newItem: Tv
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Tv,
            newItem: Tv
        ): Boolean {
           return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        if (viewType == MOVIE_VIEW_TYPE) {
            return TvItemViewHolder(ItemTvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//        } else {
//            __binding = ErrorStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            return NetworkStateItemViewHolder(bbinding.root)
//        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//               if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
        getItem(position)?.let { (holder as TvItemViewHolder).bindPost(it) }
//        }
//        else {
//            (holder as NetworkStateItemViewHolder).bindPost(networkState)
//        }
    }

//    private fun hasExtraRow(): Boolean {
//        return networkState != null && networkState != NetworkState.LOADED
//    }
//
//    override fun getItemCount(): Int {
//        Log.e("myLogs",(super.getItemCount() + if (hasExtraRow()) 1 else 0).toString())
//        return super.getItemCount() + if (hasExtraRow()) 1 else 0
//    }

//    override fun getItemCount(): Int {
//        return getCurrSize()
//    }
//
//
//    private fun getCurrSize(): Int {
//        if (currSize != currentList?.size && !currentList.isNullOrEmpty()){
//            currentList?.let { currSize = it.size }
//            currentList?.let { addTvToDB(it) }
//            Log.e("MyLogs", currentList?.size.toString())
//            return currentList?.size!!
//        } else if (currSize == currentList?.size) {
//            return currSize
//        }
//        return 0
//    }

//    override fun getItemViewType(position: Int): Int {
//        return if (hasExtraRow() && position == itemCount - 1) {
//            NETWORK_VIEW_TYPE
//        } else {
//            MOVIE_VIEW_TYPE
//        }
//    }

//    private fun addTvToDB(list: PagedList<Tv>) {
//        try {
//            realm.beginTransaction()
//
//            realm.copyToRealmOrUpdate(list)
//            realm.commitTransaction()
//            Log.e("myLogsDB",list.size.toString())
//
//        } catch (e:Exception) {
//            Log.e("myLogs", "Error ${e.message}")
//        }
//    }

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

//    inner class NetworkStateItemViewHolder (view: View): RecyclerView.ViewHolder(view) {
//
//        fun bindPost(networkState: NetworkState?) {
//            if (networkState != null && networkState == NetworkState.LOADING) {
//                bbinding.progressBarError.visibility = View.VISIBLE;
//            }
//            else  {
//                bbinding.progressBarError.visibility = View.GONE;
//            }
//
//            if (networkState != null && networkState == NetworkState.ERROR) {
//                bbinding.errorTxt.visibility = View.VISIBLE;
//                bbinding.errorTxt.text = networkState.msg;
//            }
//            else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
//                bbinding.errorTxt.visibility = View.VISIBLE;
//                bbinding.errorTxt.text = networkState.msg;
//            }
//            else {
//                bbinding.errorTxt.visibility = View.GONE;
//            }
//
//
//        }
//    }
//
//    fun setNetworkState(newNetworkState: NetworkState) {
//        val previousState = this.networkState
//        val hadExtraRow = hasExtraRow()
//        this.networkState = newNetworkState
//        val hasExtraRow = hasExtraRow()
//
//        if (hadExtraRow != hasExtraRow) {
//            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
//                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
//            } else {                                       //hasExtraRow is true and hadExtraRow false
//                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
//            }
//        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
//            notifyItemChanged(itemCount - 1)       //add the network message at the end
//        }
//
//    }


}