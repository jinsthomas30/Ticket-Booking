package com.example.flightticketingbooking.airportcity.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CityResponse(
    val `data`: List<Data>,
    val meta: Meta
):Parcelable
@Parcelize
data class Data(
    val address: Address,
    val analytics: Analytics,
    val detailedName: String,
    val geoCode: GeoCode,
    val iataCode: String,
    val id: String,
    val name: String,
    val self: Self,
    val subType: String,
    val timeZoneOffset: String,
    val type: String
):Parcelable
@Parcelize
data class Meta(
    val count: Int,
    val links: Links
):Parcelable
@Parcelize
data class Address(
    val cityCode: String,
    val cityName: String,
    val countryCode: String,
    val countryName: String,
    val regionCode: String,
    val stateCode: String
):Parcelable
@Parcelize
data class Analytics(
    val travelers: Travelers
):Parcelable
@Parcelize
data class GeoCode(
    val latitude: Double,
    val longitude: Double
):Parcelable
@Parcelize
data class Self(
    val href: String,
    val methods: List<String>
):Parcelable
@Parcelize
data class Travelers(
    val score: Int
):Parcelable
@Parcelize
data class Links(
    val self: String
):Parcelable