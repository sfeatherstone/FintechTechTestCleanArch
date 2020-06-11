package com.sfeatherstone.propertyPriceAverage.repository.network.model

import kotlinx.serialization.*

@Serializable
data class PropertyNetwork(
    val id: Int,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val number: String,
    val address: String,
    val region: String,
    val postcode: String,
    val propertyType: String
)