package com.sfeatherstone.propertyPriceAverage.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.sfeatherstone.propertyPriceAverage.model.PropertyPriceAverage
import com.sfeatherstone.propertyPriceAverage.model.PropertyPriceAverageState
import com.sfeatherstone.propertyPriceAverage.useCase.GetPropertyPriceAverage
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.lang.RuntimeException
import java.util.*

class PriceAverageViewModelTest{

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `when useCase returns valid data then livedata shows correct states when no errors`() {

        val getAccount = mock<GetPropertyPriceAverage> {
            on { invoke(any(), any(), any()) } doAnswer {
                val callback: (Either<Exception, PropertyPriceAverage>) -> Unit = it.getArgument(2)
                callback.invoke(Either.right(
                    PropertyPriceAverage(1010,2323)
                ))
            }
        }

        val mainViewModel = PriceAverageViewModel(
            getAccount
        )

        runBlocking {
            mainViewModel.getPropertyPriceAverage()
            assertEquals(PropertyPriceAverageState.Success::class, requireNotNull(mainViewModel.propertyPriceAverageState.value)::class)

            assertEquals(
                PropertyPriceAverage(1010,2323),
                (mainViewModel.propertyPriceAverageState.value as PropertyPriceAverageState.Success).data
            )

        }

    }

    @Test
    fun `when useCase returns valid data then livedata shows correct states when exception`() {

        val getAccount = mock<GetPropertyPriceAverage> {
            on { invoke(any(), any(), any()) } doAnswer {
                val callback: (Either<Exception, PropertyPriceAverage>) -> Unit = it.getArgument(2)
                callback.invoke(Either.left(
                    RuntimeException("Error")
                ))
            }
        }

        val mainViewModel = PriceAverageViewModel(
            getAccount
        )

        runBlocking {
            mainViewModel.getPropertyPriceAverage()
            assertEquals(
                PropertyPriceAverageState.Error::class,
                requireNotNull(mainViewModel.propertyPriceAverageState.value)::class
            )

            assertEquals(
                RuntimeException::class,
                (requireNotNull(mainViewModel.propertyPriceAverageState.value) as PropertyPriceAverageState.Error).ex::class
            )

        }

    }


}