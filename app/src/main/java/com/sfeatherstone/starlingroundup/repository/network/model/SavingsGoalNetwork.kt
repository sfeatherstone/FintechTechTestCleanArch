package com.sfeatherstone.starlingroundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class SavingsGoalNetwork(
    val savingsGoalUid: String?,
    val name: String,
    val totalSaved: Money)