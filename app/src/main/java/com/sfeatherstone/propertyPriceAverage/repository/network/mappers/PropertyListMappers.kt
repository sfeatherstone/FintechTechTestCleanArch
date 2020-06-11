package com.sfeatherstone.propertyPriceAverage.repository.network.mappers

import com.sfeatherstone.propertyPriceAverage.model.Property
import com.sfeatherstone.propertyPriceAverage.model.PropertyType
import com.sfeatherstone.propertyPriceAverage.repository.network.model.PropertyListNetwork


fun PropertyListNetwork.toPropertyList(): List<Property> {
    return this.properties.map {
        //Convert to enum
        val propertyType = try {
            PropertyType.valueOf(it.propertyType)
        } catch (e: IllegalArgumentException) {
            PropertyType.UNKNOWN
        }

        Property(
            id = it.id,
            price = it.price,
            bedrooms = it.bedrooms,
            bathrooms = it.bathrooms,
            number = it.number,
            address = it.address,
            region = it.region,
            postcode = it.postcode,
            propertyType = propertyType
        )
    }
}
