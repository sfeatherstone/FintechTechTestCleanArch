package com.sfeatherstone.propertyPriceAverage.model

sealed class PropertyPriceAverageState {
    object Loading : PropertyPriceAverageState()
    class Success(val data: PropertyPriceAverage) : PropertyPriceAverageState()
    data class Error(val ex: Exception) : PropertyPriceAverageState()
}