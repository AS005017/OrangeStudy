package com.labs.orangestudy.ui.main.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.labs.orangestudy.R
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.databinding.FragmentTvBinding
import com.labs.orangestudy.ui.main.adapter.TvAdapterPagedList
import com.labs.orangestudy.ui.main.viewmodel.SearchViewModel
import com.labs.orangestudy.ui.main.viewmodel.TvViewModel
import com.labs.orangestudy.utils.NetworkState
import com.labs.orangestudy.utils.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm


@AndroidEntryPoint
class TvFragment : Fragment(R.layout.fragment_tv){

    private val tvViewModel: TvViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvAdapter: TvAdapterPagedList
    private lateinit var realm: Realm

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        realm = Realm.getDefaultInstance()
        setupRVandGetData()

    }

    private fun setupRVandGetData() {
        binding.rvTv.layoutManager = LinearLayoutManager(requireContext())

        tvAdapter = TvAdapterPagedList(tvViewModel.tvPagedListLiveData)
        binding.rvTv.adapter = tvAdapter
        binding.swipeRefresh.setOnRefreshListener {
            realm.beginTransaction()
            val notFavorite = realm.where(Tv::class.java).notEqualTo("favorite", true).findAll()
            Log.e("DeleteFromRealm", notFavorite.size.toString())
            notFavorite.deleteAllFromRealm()
            realm.commitTransaction()
            tvViewModel.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
//        tvViewModel.tvPagedListLiveData.observe(viewLifecycleOwner) { pagedList ->
//                tvAdapter.submitList(pagedList)
//                Log.e("TvList", pagedList.size.toString())
//            }
//
        tvViewModel.networkState.observe(viewLifecycleOwner) {
            binding.progressBarTv.visibility =
                if (tvViewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtErrorPopular.visibility =
                if (tvViewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

        }
        (binding.rvTv.adapter as TvAdapterPagedList).onItemClick = { tv ->
                val action = TvFragmentDirections.navigationTvToDetailFragment(tv.id)
                Navigation.findNavController(binding.root).navigate(action)
            }
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        var item = menu.findItem(R.id.action_search)
        val searchView: SearchView = item.actionView as SearchView
        searchView.onQueryTextChanged {
            //tvViewModel.searchQuery.value = it
            Toast.makeText(context,it,Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        realm.beginTransaction()
//        realm.deleteAll() //clear realm db?
//        realm.commitTransaction()
        _binding = null
    }
}
