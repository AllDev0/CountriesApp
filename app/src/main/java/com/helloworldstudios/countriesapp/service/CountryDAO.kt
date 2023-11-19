package com.helloworldstudios.countriesapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.helloworldstudios.countriesapp.model.Country

@Dao
interface CountryDAO {
    @Insert
    suspend fun insertAll(vararg countries: Country): List<Long>

    @Query("SELECT * FROM COUNTRY")
    suspend fun getAllCountries(): List<Country>

    @Query("SELECT * FROM COUNTRY WHERE country_id = :countryId")
    suspend fun getCountry(countryId: Int): Country

    @Query("DELETE FROM COUNTRY")
    suspend fun deleteAllCountries()
}