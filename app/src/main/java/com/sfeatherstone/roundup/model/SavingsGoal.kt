package com.sfeatherstone.roundup.model

import java.util.*

data class SavingsGoal(
    val savingsGoalUid: UUID,
    val name: String,
    val amount: Long)