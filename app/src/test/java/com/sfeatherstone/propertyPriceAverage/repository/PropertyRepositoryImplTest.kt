package com.sfeatherstone.propertyPriceAverage.repository

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sfeatherstone.propertyPriceAverage.model.Property
import com.sfeatherstone.propertyPriceAverage.model.PropertyType
import com.sfeatherstone.propertyPriceAverage.repository.network.PropertiesApi
import com.sfeatherstone.propertyPriceAverage.repository.network.model.PropertyListNetwork
import com.sfeatherstone.propertyPriceAverage.repository.network.model.PropertyNetwork
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

class PropertyRepositoryImplTest {

    @Test
    fun `when PropertiesApi returns empty list then getProperties also returns empty list`() {
        val propertiesApi: PropertiesApi = mock()
        val propertyRepositoryImpl = PropertyRepositoryImpl(propertiesApi)


        runBlocking {
            whenever(propertiesApi.getProperties()) doReturn PropertyListNetwork(emptyList())

            val properties = propertyRepositoryImpl.getProperties()
            assertEquals(emptyList<Property>(), properties)
        }
    }


    @Test
    fun `when PropertiesApi returns valid data then getProperties also returns converted domain data`() {
        val propertiesApi: PropertiesApi = mock()
        val propertyRepositoryImpl = PropertyRepositoryImpl(propertiesApi)


        runBlocking {
            whenever(propertiesApi.getProperties()) doReturn PropertyListNetwork(listOf(pn1,pn2))

            val properties = propertyRepositoryImpl.getProperties()
            assertEquals(listOf(p1,p2), properties)
        }

        runBlocking {
            whenever(propertiesApi.getProperties()) doReturn PropertyListNetwork(listOf(pn3, pn2, pn1))

            val properties = propertyRepositoryImpl.getProperties()
            assertEquals(listOf(p3, p2, p1), properties)
        }

    }

    companion object {
        val pn1 = PropertyNetwork(1,10000,1,2,"n1","a1","r1","p1", "FLAT")
        val pn2 = PropertyNetwork(2,10010,3,4,"n2","a2","r2","p2", "SEMI_DETACHED")
        val pn3 = PropertyNetwork(3,10_010_000,5,6,"n3","a3","r3","p3", "CASTLE")

        val p1 = Property(1,10000,1,2,"n1","a1","r1","p1", PropertyType.FLAT)
        val p2 = Property(2,10010,3,4,"n2","a2","r2","p2", PropertyType.SEMI_DETACHED)
        val p3 = Property(3,10_010_000,5,6,"n3","a3","r3","p3",  PropertyType.UNKNOWN)
    }

}