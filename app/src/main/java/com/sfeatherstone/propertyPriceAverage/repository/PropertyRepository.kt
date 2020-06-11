package com.sfeatherstone.propertyPriceAverage.repository

import com.sfeatherstone.propertyPriceAverage.model.Property
import com.sfeatherstone.propertyPriceAverage.repository.network.PropertiesApi
import com.sfeatherstone.propertyPriceAverage.repository.network.mappers.toPropertyList


interface PropertyRepository {
    suspend fun getProperties(): List<Property>
}