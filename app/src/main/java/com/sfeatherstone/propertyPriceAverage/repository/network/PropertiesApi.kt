package com.sfeatherstone.propertyPriceAverage.repository.network

import com.sfeatherstone.propertyPriceAverage.repository.network.model.PropertyListNetwork
import retrofit2.http.GET


interface PropertiesApi {
    @GET("properties.json")
    suspend fun getProperties(
    ): PropertyListNetwork
}
