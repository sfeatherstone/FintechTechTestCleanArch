package com.sfeatherstone.propertyPriceAverage.model

data class Property(
    val id: Int,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val number: String,
    val address: String,
    val region: String,
    val postcode: String,
    val propertyType: PropertyType
)