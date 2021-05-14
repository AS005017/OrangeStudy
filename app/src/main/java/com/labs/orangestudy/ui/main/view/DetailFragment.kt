package com.labs.orangestudy.ui.main.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.labs.orangestudy.R
import com.labs.orangestudy.data.api.TvApi
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.databinding.FragmentDetailBinding
import com.labs.orangestudy.ui.main.adapter.SeasonsAdapter
import com.labs.orangestudy.ui.main.viewmodel.DetailViewModel
import com.labs.orangestudy.utils.NetworkState
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args:DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.backDetail.setOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })
        setDataToViews()
    }

    private fun setDataToViews() {
        val id = args.id
        viewModel.refreshTv(id)
        viewModel.networkState.observe(viewLifecycleOwner) { netState ->
            binding.progressBarDetail.visibility = if (netState == NetworkState.LOADING) View.VISIBLE else View.GONE
            if (netState == NetworkState.ERROR) {
                Toast.makeText(
                    context,
                    context?.resources?.getString(R.string.you_in_offline),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

            viewModel.tvByIdLiveData.observe(viewLifecycleOwner) { response ->
                if (response == null) {
                    Toast.makeText(context, "Error in call", Toast.LENGTH_SHORT).show()
                    return@observe
                }

                binding.detailTvTitle.text = response.name
                binding.detailTvLastAirDate.text = response.lastAirDate
                binding.detailTvRate.text = response.voteAverage.toString()
                binding.detailTvDesc.text = response.overview
                binding.detailTvSeasonsCount.text = response.numberOfSeasons.toString()

                Picasso.get()
                    .load(TvApi.POSTER_BASE_URL + response.backdropPath)
                    .placeholder(R.drawable.loading)
                    .into(binding.detailTvCover)

                Picasso.get()
                    .load(TvApi.POSTER_BASE_URL + response.posterPath)
                    .placeholder(R.drawable.loading)
                    .into(binding.detailTvImg)

                var genres = ""
                var i = 0
                response.genres.forEach { genre ->
                    genres += genre.name
                    if (i < response.genres.size-1) {
                        genres += ", "
                        i++
                    }
                }

                binding.detailTvGenres.text = genres

                binding.RvSeasons.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                binding.RvSeasons.adapter = SeasonsAdapter(response.seasons)

            }
        if (!isConnected())   {
            val realm: Realm = Realm.getDefaultInstance()
            val tv = realm.where(Tv::class.java).equalTo("id", id).findFirst()
            binding.detailTvTitle.text = tv?.name
            binding.detailTvLastAirDate.text = context?.resources?.getString(R.string.offline)
            binding.detailTvRate.text = tv?.voteAverage.toString()
            binding.detailTvDesc.text = tv?.overview
            binding.detailTvSeasonsName.text = context?.resources?.getString(R.string.seasons_offline)
            binding.detailTvSeasonsCount.text = context?.resources?.getString(R.string.offline)

            Picasso.get()
                .load(TvApi.POSTER_BASE_URL + tv?.backdropPath)
                .placeholder(R.drawable.loading)
                .into(binding.detailTvCover)

            Picasso.get()
                .load(TvApi.POSTER_BASE_URL + tv?.posterPath)
                .placeholder(R.drawable.loading)
                .into(binding.detailTvImg)
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw)
            actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(
                NetworkCapabilities.TRANSPORT_BLUETOOTH
            ))
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo
            nwInfo != null && nwInfo.isConnected
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}