package com.helloworldstudios.countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.helloworldstudios.countriesapp.R
import com.helloworldstudios.countriesapp.databinding.FragmentCountryDetailBinding
import com.helloworldstudios.countriesapp.util.downloadFromUrl
import com.helloworldstudios.countriesapp.util.placeHolderProgressBar
import com.helloworldstudios.countriesapp.viewmodel.CountryDetailViewModel

class CountryDetailFragment : Fragment() {
    private lateinit var binding: FragmentCountryDetailBinding
    private lateinit var countryDetailViewModel: CountryDetailViewModel
    private var countryUUID = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //binding = FragmentCountryDetailBinding.inflate(inflater, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_country_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUUID = CountryDetailFragmentArgs.fromBundle(it).countryUUID
        }

        countryDetailViewModel = ViewModelProvider(this).get(CountryDetailViewModel::class.java)
        countryDetailViewModel.getDataFromRoom(countryUUID)

        observeLiveData()
    }

    private fun observeLiveData(){
        countryDetailViewModel.countryDetail.observe(viewLifecycleOwner, Observer{ country ->
            country?.let {
                binding.selectedCountry = it
//                binding.countryDetailFragmentIV.downloadFromUrl(it.countryFlagUrl, placeHolderProgressBar(binding.root.context))
//                binding.countryDetailFragmentCountryNameTV.text = "Country Name : ${it.countryName}"
//                binding.countryDetailFragmentCountryRegionTV.text = "Country Region : ${it.countryRegion}"
//                binding.countryDetailFragmentCountryCapitalTV.text = "Country Capital : ${it.countryCapital}"
//                binding.countryDetailFragmentCountryCurrencyTV.text = "Country Currency : ${it.countryCurrency}"
//                binding.countryDetailFragmentCountryLanguageTV.text = "Country Language : ${it.countryLanguage}"
            }
        })
    }
}