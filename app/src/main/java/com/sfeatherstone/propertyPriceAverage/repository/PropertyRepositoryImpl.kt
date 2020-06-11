package com.sfeatherstone.propertyPriceAverage.repository

import com.sfeatherstone.propertyPriceAverage.model.Property
import com.sfeatherstone.propertyPriceAverage.repository.network.PropertiesApi
import com.sfeatherstone.propertyPriceAverage.repository.network.mappers.toPropertyList


class PropertyRepositoryImpl(private val propertiesApi: PropertiesApi): PropertyRepository {

    override suspend fun getProperties(): List<Property> {
        val result = propertiesApi.getProperties()
        return result.toPropertyList()
    }
}