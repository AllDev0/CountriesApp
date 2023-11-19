package com.helloworldstudios.countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.helloworldstudios.countriesapp.adapter.CountryAdapter
import com.helloworldstudios.countriesapp.databinding.FragmentFeedBinding
import com.helloworldstudios.countriesapp.viewmodel.FeedViewModel

class FeedFragment : Fragment() {
    private lateinit var binding: FragmentFeedBinding
    private lateinit var feedViewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        feedViewModel.refreshData()
        binding.feedFragmentRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.feedFragmentRecyclerView.adapter = countryAdapter
        binding.feedFragmentSwipeRefreshLayout.setOnRefreshListener {
            binding.feedFragmentRecyclerView.visibility = View.GONE
            binding.feedFragmentErrorTextView.visibility = View.GONE
            binding.feedFragmentSwipeRefreshLayout.isRefreshing = false
            //binding.feedFragmentProgressBar.visibility = View.VISIBLE
            feedViewModel.refreshFromAPI()


        }
        observeLiveData()
    }

    private fun observeLiveData(){
        feedViewModel.countryList.observe(viewLifecycleOwner, Observer{ countries ->
            countries?.let {
                binding.feedFragmentRecyclerView.visibility = View.VISIBLE
                countryAdapter.updateCountryList(it)
            }
        })

        feedViewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it){
                    binding.feedFragmentErrorTextView.visibility = View.VISIBLE
                } else{
                    binding.feedFragmentErrorTextView.visibility = View.GONE
                }

            }
        })

        feedViewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it){
                    binding.feedFragmentProgressBar.visibility = View.VISIBLE
                    binding.feedFragmentRecyclerView.visibility = View.GONE
                    binding.feedFragmentErrorTextView.visibility = View.GONE
                } else{
                    binding.feedFragmentProgressBar.visibility = View.GONE
                }
            }
        })
    }

}