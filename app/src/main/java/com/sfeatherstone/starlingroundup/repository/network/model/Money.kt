package com.sfeatherstone.starlingroundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class Money(val minorUnits: Long, val currency: String = "GBP")