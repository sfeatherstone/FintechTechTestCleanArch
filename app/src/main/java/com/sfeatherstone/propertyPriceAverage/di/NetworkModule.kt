package com.sfeatherstone.propertyPriceAverage.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sfeatherstone.propertyPriceAverage.API_ENDPOINT
import com.sfeatherstone.propertyPriceAverage.repository.network.PropertiesApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create


val networkModule = module {

    single {
        OkHttpClient.Builder().build()
    }

    single <Retrofit> {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .client(get())
            .addConverterFactory(Json(JsonConfiguration(ignoreUnknownKeys = true)).asConverterFactory(contentType))
            .build()
    }

    single<PropertiesApi> { get<Retrofit>().create() }
}
