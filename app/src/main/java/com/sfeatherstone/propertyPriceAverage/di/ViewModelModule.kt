package com.sfeatherstone.propertyPriceAverage.di

import com.sfeatherstone.propertyPriceAverage.ui.PriceAverageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        PriceAverageViewModel(
            get()
        )
    }
}
