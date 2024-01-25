package com.example.flightticketingbooking.respository

import com.example.flightticketingbooking.airportcity.model.CityRequest
import com.example.flightticketingbooking.airportcity.model.CityResponse
import com.example.flightticketingbooking.dashboard.model.LoginRequest
import com.example.flightticketingbooking.dashboard.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body

interface MyRepository {
    suspend fun doNetworkCal() : Response<String>
    suspend fun login(loginRequest: LoginRequest) : Response<LoginResponse>
    suspend fun getAirportCity(cityRequest: CityRequest) : Response<CityResponse>
}