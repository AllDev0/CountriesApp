package com.helloworldstudios.countriesapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.helloworldstudios.countriesapp.model.Country
import com.helloworldstudios.countriesapp.service.CountryApiService
import com.helloworldstudios.countriesapp.service.CountryDatabase
import com.helloworldstudios.countriesapp.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {
    private val countryApiService = CountryApiService()
    private val disposable = CompositeDisposable()
    private val customSharedPreferences = CustomSharedPreferences(getApplication())
    private val refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val countryList = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime = customSharedPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        } else{
            getDataFromAPI()
        }
    }

    fun refreshFromAPI() = getDataFromAPI()

    private fun getDataFromSQLite(){
        countryLoading.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries From SQLite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromAPI(){
        countryLoading.value = true
        disposable.add(countryApiService.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                override fun onSuccess(t: List<Country>) {
                    storeInSQLite(t)
                    Toast.makeText(getApplication(), "Countries From API", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    countryLoading.value = false
                    countryError.value = true
                    e.printStackTrace()
                }
            })
        )
    }

    private fun showCountries(t: List<Country>){
        countryList.value = t
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list: List<Country>){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            var uuidList = dao.insertAll(*list.toTypedArray())
            uuidList.indices.forEach{
                list[it].countryUuid = uuidList[it].toInt()
            }
            showCountries(list)
        }

        customSharedPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}