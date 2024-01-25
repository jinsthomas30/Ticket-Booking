package com.example.flightticketingbooking.dashboard.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("application_name")
    val application_name: String,
    @SerializedName("client_id")
    val client_id: String,
    @SerializedName("expires_in")
    val expires_in: Int,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("token_type")
    val token_type: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("username")
    val username: String
)