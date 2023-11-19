package com.helloworldstudios.countriesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.helloworldstudios.countriesapp.databinding.RowCountryBinding
import com.helloworldstudios.countriesapp.model.Country
import com.helloworldstudios.countriesapp.util.downloadFromUrl
import com.helloworldstudios.countriesapp.util.placeHolderProgressBar
import com.helloworldstudios.countriesapp.view.FeedFragmentDirections

class CountryAdapter(private val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryRowViewHolder>() {
    inner class CountryRowViewHolder(var binding: RowCountryBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryRowViewHolder {
        val binding = RowCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryRowViewHolder(binding)
    }

    override fun getItemCount(): Int = countryList.size

    override fun onBindViewHolder(holder: CountryRowViewHolder, position: Int) {
        holder.binding.countryRowCountryTextView.text = countryList[position].countryName
        holder.binding.countryRowCountryRegionTextView.text = countryList[position].countryRegion
        holder.binding.countryRowImageView.downloadFromUrl(countryList.get(position).countryFlagUrl, placeHolderProgressBar(holder.binding.root.context))
        holder.binding.countryRow.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryDetailFragment(countryList.get(position).countryUuid)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateCountryList(updatedCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(updatedCountryList)
        notifyDataSetChanged()
    }
}