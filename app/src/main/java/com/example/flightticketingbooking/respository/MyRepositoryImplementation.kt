package com.example.flightticketingbooking.respository

import com.example.flightticketingbooking.airportcity.model.CityRequest
import com.example.flightticketingbooking.airportcity.model.CityResponse
import com.example.flightticketingbooking.dashboard.model.LoginRequest
import com.example.flightticketingbooking.dashboard.model.LoginResponse
import com.example.flightticketingbooking.network.AppApis
import retrofit2.Response

class MyRepositoryImplementation(
    private val api : AppApis
): MyRepository {
    override suspend fun doNetworkCal() : Response<String> {
        return api.doNetworkCall()
    }

    override suspend fun login(loginRequest: LoginRequest) : Response<LoginResponse> {
        return api.login(loginRequest.grant_type,loginRequest.client_id,loginRequest.client_secret)
    }

    override suspend fun getAirportCity(cityRequest: CityRequest): Response<CityResponse> {
        return api.getAirportCity(cityRequest.token,cityRequest.keyword,cityRequest.subType)
    }
}