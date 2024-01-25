package com.example.flightticketingbooking.dashboard.viewModel

import android.icu.util.Calendar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightticketingbooking.Constants
import com.example.flightticketingbooking.dashboard.model.LoginRequest
import com.example.flightticketingbooking.respository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel@Inject constructor(
    private val repository: MyRepository
): ViewModel() {
    var mFromCity:String=""
    var mToCity:String=""
    var passengerCount:Int=1
    val token : MutableLiveData<String> = MutableLiveData()
    val adultCount : MutableLiveData<String> = MutableLiveData()
    var mFromDate=""
    var mToDate=""
    var mTripType:Int=0
    var fromCal = Calendar.getInstance()

    fun getToken(){
        viewModelScope.launch {
            try {
                repository.login(
                    LoginRequest(
                        Constants.GRANT_TYPE,
                        Constants.CLIENT_ID,
                        Constants.CLIENT_SECRET
                    )
                ).let {
                    if (it.code() == 200) {
                        token.value=it.body()?.access_token
                    } else {
                        println("$$$" + it.code())
                        println("$$$" + it.message())
                    }
                }
            }catch (e:Exception){
                println("$$$" + e.printStackTrace())
            }

        }
    }


    fun addPassenger(){
        if(passengerCount!=8){
            passengerCount += 1
            adultCount.value=passengerCount.toString()
        }
    }

    fun reducePassenger(){
        if(passengerCount!=1){
            passengerCount -= 1
            adultCount.value=passengerCount.toString()
        }
    }

    fun setFromDate(FromDate:String){
        this.mFromDate=FromDate
    }
    fun setToDate(toDate:String){
        this.mToDate=toDate
    }
    fun setAirportCity(fromCity:String){
        this.mFromCity=fromCity;
    }
    fun setAirportToCity(toCity:String){
        this.mToCity=toCity
    }

    fun setTripType(tripType:Int){
        this.mTripType=tripType
    }

    fun setFromCalenderInstance(fromCalenderInstance:Calendar){
        this.fromCal=fromCalenderInstance
    }



}