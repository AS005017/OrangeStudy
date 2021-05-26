package com.labs.orangestudy.ui.main.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.labs.orangestudy.R
import com.labs.orangestudy.utils.NetworkState
import com.labs.orangestudy.databinding.FragmentTvBinding
import com.labs.orangestudy.ui.main.adapter.TvAdapterPagedList
import com.labs.orangestudy.ui.main.viewmodel.TvViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm


@AndroidEntryPoint
class TvFragment : Fragment(R.layout.fragment_tv) {

    private val tvViewModel: TvViewModel by viewModels()

    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvAdapter: TvAdapterPagedList
//    private lateinit var realm: Realm

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        realm = Realm.getDefaultInstance()
        setupRVandGetData()

    }

    private fun setupRVandGetData() {
        binding.rvTv.layoutManager = LinearLayoutManager(requireContext())

        //tvAdapter = TvAdapterPagedList(realm)
        binding.rvTv.adapter = TvAdapterPagedList(tvViewModel.tvPagedListLiveData)

//        tvViewModel.tvPagedListLiveData.observe(viewLifecycleOwner) { pagedList ->
//                tvAdapter.submitList(pagedList)
//                Log.e("TvList", pagedList.size.toString())
//            }
//
//        tvViewModel.networkState.observe(viewLifecycleOwner) {
//            binding.progressBarTv.visibility =
//                if (tvViewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
//            binding.txtErrorPopular.visibility =
//                if (tvViewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

//            if (!tvViewModel.listIsEmpty()) {
//                tvAdapter.setNetworkState(it)
//            }
//        }
//            tvAdapter.onItemClick = { tv ->
//                val action = TvFragmentDirections.navigationTvToDetailFragment(tv.id)
//                Navigation.findNavController(binding.root).navigate(action)
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        realm.beginTransaction()
//        realm.deleteAll() //clear realm db?
//        realm.commitTransaction()
        _binding = null
    }
}
