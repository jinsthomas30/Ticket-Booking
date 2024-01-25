package com.example.flightticketingbooking.airportcity.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flightticketingbooking.airportcity.model.Data
import com.example.flightticketingbooking.databinding.CityNamesBinding

class CityAdapter(private val cityList: List<Data>,cityClickListeners : CityClickListeners) :
    RecyclerView.Adapter<CityAdapter.ItemViewHolder>() {
        lateinit var mCityClickListeners : CityClickListeners

        init {
            this.mCityClickListeners=cityClickListeners
        }

    inner class ItemViewHolder(val binding: CityNamesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CityNamesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        with(holder) {
            with(cityList[position]) {
                binding.tCity.text = address.cityName+
                        (" ("+address.cityCode+")")
                binding.tCounty.text = address.countryName
            }
            itemView.setOnClickListener{
                mCityClickListeners.cityClickListeners(cityList[holder.adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return cityList.size
    }
}