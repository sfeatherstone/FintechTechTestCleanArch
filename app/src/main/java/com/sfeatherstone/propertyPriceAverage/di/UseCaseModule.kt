package com.sfeatherstone.propertyPriceAverage.di

import com.sfeatherstone.propertyPriceAverage.useCase.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetPropertyPriceAverage(get()) }
}