package com.sfeatherstone.roundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class AddToSavingsGoalRequest(val amount: Money)