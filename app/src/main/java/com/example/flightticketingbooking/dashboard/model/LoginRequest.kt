package com.example.flightticketingbooking.dashboard.model

import com.google.gson.annotations.SerializedName

data class LoginRequest (

    @SerializedName("grant_type")
    var grant_type:String,

    @SerializedName("client_id")
    var client_id: String,
    
    @SerializedName("client_secret")
    var client_secret: String


)