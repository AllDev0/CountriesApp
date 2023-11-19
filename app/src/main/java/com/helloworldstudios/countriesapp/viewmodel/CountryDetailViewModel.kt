package com.helloworldstudios.countriesapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.helloworldstudios.countriesapp.model.Country
import com.helloworldstudios.countriesapp.service.CountryDAO
import com.helloworldstudios.countriesapp.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryDetailViewModel(application: Application) : BaseViewModel(application) {
    val countryDetail = MutableLiveData<Country>()

    fun getDataFromRoom(countryId: Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(countryId)
            countryDetail.value = country
        }
    }
}