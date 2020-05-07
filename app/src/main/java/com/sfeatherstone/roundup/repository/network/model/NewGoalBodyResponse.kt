package com.sfeatherstone.roundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class NewGoalBodyResponse(val savingsGoalUid: String)