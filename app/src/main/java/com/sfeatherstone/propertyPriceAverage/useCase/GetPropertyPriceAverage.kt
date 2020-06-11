package com.sfeatherstone.propertyPriceAverage.useCase

import arrow.core.Either
import com.sfeatherstone.propertyPriceAverage.model.PropertyPriceAverage
import com.sfeatherstone.propertyPriceAverage.repository.PropertyRepository
import timber.log.Timber


class GetPropertyPriceAverage(
    private val propertyRepository: PropertyRepository
) : UseCase<PropertyPriceAverage, Unit>() {

    override suspend fun run(params: Unit): Either<Exception, PropertyPriceAverage> {
        return try {
            val properties = propertyRepository.getProperties()
            val sum = properties.sumBy { it.price }
            when (properties.size) {
                0 -> Either.right(PropertyPriceAverage(0, 0))
                else -> Either.right(PropertyPriceAverage(sum/properties.size, properties.size))
            }
        } catch (ex: Exception) {
            Timber.d(ex, "Exception: $ex")
            Either.Left(ex)
        }
    }
}
