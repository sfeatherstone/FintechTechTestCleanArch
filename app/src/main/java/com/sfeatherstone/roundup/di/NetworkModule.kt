package com.sfeatherstone.roundup.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sfeatherstone.roundup.API_ACCESS_TOKEN
import com.sfeatherstone.roundup.API_ENDPOINT
import com.sfeatherstone.roundup.repository.network.AccountApi
import com.sfeatherstone.roundup.repository.network.SavingsGoalsApi
import com.sfeatherstone.roundup.repository.network.TransactionsFeedApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create


val networkModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer $API_ACCESS_TOKEN")
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    single <Retrofit> {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .client(get())
            .addConverterFactory(Json(JsonConfiguration(ignoreUnknownKeys = true)).asConverterFactory(contentType))
            .build()
    }

    single<TransactionsFeedApi> { get<Retrofit>().create() }
    single<AccountApi> { get<Retrofit>().create() }
    single<SavingsGoalsApi> { get<Retrofit>().create() }
}
