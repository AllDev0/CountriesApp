package com.helloworldstudios.countriesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.helloworldstudios.countriesapp.R
import com.helloworldstudios.countriesapp.databinding.RowCountryBinding
import com.helloworldstudios.countriesapp.model.Country
import com.helloworldstudios.countriesapp.util.downloadFromUrl
import com.helloworldstudios.countriesapp.util.placeHolderProgressBar
import com.helloworldstudios.countriesapp.view.FeedFragmentDirections

class CountryAdapter(private val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryRowViewHolder>(), CountryClickListener {
    inner class CountryRowViewHolder(var binding: RowCountryBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryRowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RowCountryBinding>(inflater, R.layout.row_country, parent, false)
        return CountryRowViewHolder(view)
    }

    override fun getItemCount(): Int = countryList.size

    override fun onBindViewHolder(holder: CountryRowViewHolder, position: Int) {
        holder.binding.root.tag = holder.binding
        holder.binding.countryRow.setOnClickListener {
            onCountryClicked(it)
        }
        holder.binding.country = countryList[position]
        holder.binding.listener = this
    }

    fun updateCountryList(updatedCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(updatedCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {
        val binding = v.tag as? RowCountryBinding ?: return
        val uuid = binding.country!!.countryUuid
        val action = FeedFragmentDirections.actionFeedFragmentToCountryDetailFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}