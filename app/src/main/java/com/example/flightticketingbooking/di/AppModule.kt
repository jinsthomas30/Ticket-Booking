package com.example.flightticketingbooking.di

import com.example.flightticketingbooking.network.AppApis
import com.example.flightticketingbooking.respository.MyRepository
import com.example.flightticketingbooking.respository.MyRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //dependency will live as as the application does
object AppModule {

    private const val BASE_URL =  "https://test.api.amadeus.com/v1/"

    @Provides
    @Singleton //marks the scope of the dependency. i.e will have the single instance through out
    fun provideMyApi(): AppApis {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .build()

        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(AppApis::class.java)
    }


    @Provides
    @Singleton
    fun provideMyRepository(api : AppApis) : MyRepository {
        return MyRepositoryImplementation(api)
    }

}