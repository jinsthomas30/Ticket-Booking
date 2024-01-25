package com.example.flightticketingbooking.airportcity.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightticketingbooking.Constants
import com.example.flightticketingbooking.airportcity.model.CityRequest
import com.example.flightticketingbooking.airportcity.model.Data
import com.example.flightticketingbooking.respository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirportCityViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {

    val cities: MutableLiveData<List<Data>> = MutableLiveData()

    fun getAirportCity(token: String, keywords: String) {
        viewModelScope.launch {
            try {
                repository.getAirportCity(
                    CityRequest(
                        token,
                        keywords,
                        Constants.SUB_TYPE
                    )
                ).let {
                    if (it.code() == 200) {
                        if (!it.body()?.data.isNullOrEmpty()) {
                            cities.value = it.body()?.data
                        }
                    } else {
                        println("CityResponse" + it.code())
                        println("CityResponse" + it.message())
                    }

                }
            } catch (e: Exception) {
                println("$$$" + e.printStackTrace())
            }

        }
    }

}