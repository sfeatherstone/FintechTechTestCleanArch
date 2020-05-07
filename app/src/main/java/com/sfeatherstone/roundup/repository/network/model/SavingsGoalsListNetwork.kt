package com.sfeatherstone.roundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class SavingsGoalsListNetwork(val savingsGoalList: List<SavingsGoalNetwork>)