package com.sfeatherstone.propertyPriceAverage.useCase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sfeatherstone.propertyPriceAverage.model.Property
import com.sfeatherstone.propertyPriceAverage.model.PropertyPriceAverage
import com.sfeatherstone.propertyPriceAverage.model.PropertyType
import com.sfeatherstone.propertyPriceAverage.repository.PropertyRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class GetPropertyPriceAverageTest{

    @Test
    fun `when repo throws exception then return left exception`() {

        val propertyRepository: PropertyRepository = mock()

        val getAccougetPropertyPriceAveraget =
            GetPropertyPriceAverage(propertyRepository)

        runBlocking {
            whenever(propertyRepository.getProperties()).thenThrow(RuntimeException("Something went wrong"))

            val value = getAccougetPropertyPriceAveraget.run(Unit)

            value.fold(
                {
                    assertTrue(it::class == RuntimeException::class)
                    assertEquals("Something went wrong", it.message)
                },
                {
                    assertFalse("Return right value", true)
                }
            )
        }
    }


    @Test
    fun `when repo returns empty list null then return 0`() {

        val propertyRepository: PropertyRepository = mock()

        val getPropertyPriceAverage =
            GetPropertyPriceAverage(propertyRepository)

        runBlocking {
            whenever(propertyRepository.getProperties()).doReturn(emptyList())

            val value = getPropertyPriceAverage.run(Unit)

            value.fold(
                {
                    assertFalse("Return left value", true)
                },
                {
                    assertEquals("Return right value", it, PropertyPriceAverage(0, 0))
                }
            )
        }
    }


    @Test
    fun `when repo returns valid data then return right with correct data`() {
        val propertyRepository: PropertyRepository = mock()

        val getPropertyPriceAverage =
            GetPropertyPriceAverage(propertyRepository)

        runBlocking {
            for (t in testData) {
                whenever(propertyRepository.getProperties()).doReturn(t.list)

                val value = getPropertyPriceAverage.run(Unit)

                value.fold(
                    {
                        assertFalse("Return left value", true)
                    },
                    {
                        assertEquals("Return right value", it, PropertyPriceAverage(t.average, t.list.size))
                    }
                )
            }
        }
    }

    companion object {
        val p1 = Property(1,10000,0,0,"","","","",PropertyType.UNKNOWN)
        val p2 = Property(2,10010,0,0,"","","","",PropertyType.UNKNOWN)
        val p3 = Property(3,333333,0,0,"","","","",PropertyType.UNKNOWN)
        val p4 = Property(4,400_000_000,0,0,"","","","",PropertyType.UNKNOWN)

        class testValues(val list: List<Property>, val average: Int)

        val testData = listOf(
            testValues(listOf(p1), 10000),
            testValues(listOf(p4), 400_000_000),
            testValues(listOf(p1,p2,p3,p4), 100088335),
            testValues(listOf(p4,p3,p2,p1), 100088335),
            testValues(listOf(p1,p4), 200005000),
            testValues(listOf(p1,p2), 10005)
        )

    }

}