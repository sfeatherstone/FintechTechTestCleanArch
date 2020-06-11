package com.sfeatherstone.propertyPriceAverage.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sfeatherstone.propertyPriceAverage.OpenForTesting
import com.sfeatherstone.propertyPriceAverage.model.*
import com.sfeatherstone.propertyPriceAverage.useCase.*

@OpenForTesting
class PriceAverageViewModel(
    private val getPropertyPriceAverage: GetPropertyPriceAverage
): ViewModel() {

    private val propertyPriceAverageStateMutable = MutableLiveData<PropertyPriceAverageState>()
    val propertyPriceAverageState: LiveData<PropertyPriceAverageState> = propertyPriceAverageStateMutable

    fun getPropertyPriceAverage() {
        propertyPriceAverageStateMutable.value = PropertyPriceAverageState.Loading
        getPropertyPriceAverage.invoke(viewModelScope, Unit) { result ->
            result.fold(
                ifLeft = {
                    propertyPriceAverageStateMutable.value = PropertyPriceAverageState.Error(it)
                },
                ifRight = {
                    propertyPriceAverageStateMutable.value = PropertyPriceAverageState.Success(it)
                }
            )
        }
    }
}