package com.labs.orangestudy.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.labs.orangestudy.R
import com.labs.orangestudy.databinding.FragmentTvBinding
import com.labs.orangestudy.ui.main.adapter.SearchAdapter
import com.labs.orangestudy.ui.main.adapter.TvAdapterPagedList
import com.labs.orangestudy.ui.main.viewmodel.SearchViewModel
import com.labs.orangestudy.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_tv) {

    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!
    private val args:SearchFragmentArgs by navArgs()

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        searchViewModel.search(args.name)
        setupViews()
        return binding.root
    }

    fun setupViews(){
        binding.popularTvName.text = "Search results by \"${args.name}\""

        searchViewModel.tvByNameLiveData.observe(viewLifecycleOwner) {
            binding.rvTv.layoutManager = LinearLayoutManager(requireContext())
            binding.rvTv.adapter = SearchAdapter(it)
            Log.e("SearchRes", it.toString())
            binding.swipeRefresh.isRefreshing = false

            if(it.size == 0) {
                binding.txtErrorPopular.visibility = View.VISIBLE
            }
            (binding.rvTv.adapter as SearchAdapter).onItemClick = { tv ->
                val action = SearchFragmentDirections.searchFragmentToDetailFragment(tv.id)
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            searchViewModel.refresh()
        }

        searchViewModel.networkState.observe(viewLifecycleOwner) {
            binding.progressBarTv.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtErrorPopular.visibility =
                if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        }



    }


}