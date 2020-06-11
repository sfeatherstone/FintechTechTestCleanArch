package com.sfeatherstone.propertyPriceAverage.di

import com.sfeatherstone.propertyPriceAverage.repository.PropertyRepository
import com.sfeatherstone.propertyPriceAverage.repository.PropertyRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<PropertyRepository> { PropertyRepositoryImpl(get()) }
}