package com.example.flightticketingbooking.network

import com.example.flightticketingbooking.Constants
import com.example.flightticketingbooking.airportcity.model.CityResponse
import com.example.flightticketingbooking.dashboard.model.LoginRequest
import com.example.flightticketingbooking.dashboard.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AppApis {
    @GET("/products")
    suspend fun doNetworkCall(): Response<String>

    @POST("security/oauth2/token")
    @FormUrlEncoded
    suspend fun login(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Response<LoginResponse>


    @GET("reference-data/locations")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    suspend fun getAirportCity(
        @Header("Authorization") token: String,
        @Query("keyword") keyword: String,
        @Query("subType") subType: String,
    ): Response<CityResponse>
}