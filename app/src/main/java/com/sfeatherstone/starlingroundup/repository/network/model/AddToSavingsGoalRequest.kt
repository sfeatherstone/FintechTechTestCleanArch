package com.sfeatherstone.starlingroundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class AddToSavingsGoalRequest(val amount: Money)