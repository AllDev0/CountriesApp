package com.helloworldstudios.countriesapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("COUNTRY")
data class Country(
    @ColumnInfo("country_name")
    @SerializedName("name")
    val countryName: String?,
    @ColumnInfo("country_region")
    @SerializedName("region")
    val countryRegion: String?,
    @ColumnInfo("country_capital")
    @SerializedName("capital")
    val countryCapital: String?,
    @ColumnInfo("country_currency")
    @SerializedName("currency")
    val countryCurrency: String?,
    @ColumnInfo("country_language")
    @SerializedName("language")
    val countryLanguage: String?,
    @ColumnInfo("country_flag_url")
    @SerializedName("flag")
    val countryFlagUrl: String?
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("country_id")
    var countryUuid: Int = 0
}
