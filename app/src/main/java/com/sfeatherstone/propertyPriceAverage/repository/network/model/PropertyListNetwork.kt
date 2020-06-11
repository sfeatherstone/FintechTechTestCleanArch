package com.sfeatherstone.propertyPriceAverage.repository.network.model

import kotlinx.serialization.*

@Serializable
data class PropertyListNetwork(val properties: List<PropertyNetwork>)