package com.sfeatherstone.roundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class Money(val minorUnits: Long, val currency: String = "GBP")