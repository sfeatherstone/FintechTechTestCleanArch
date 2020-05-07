package com.sfeatherstone.roundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class NewGoalBodyRequest(val name: String, val currency: String)