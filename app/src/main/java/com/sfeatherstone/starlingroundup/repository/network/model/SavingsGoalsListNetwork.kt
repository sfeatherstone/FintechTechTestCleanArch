package com.sfeatherstone.starlingroundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class SavingsGoalsListNetwork(val savingsGoalList: List<SavingsGoalNetwork>)