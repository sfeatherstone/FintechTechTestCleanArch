package com.sfeatherstone.starlingroundup.model

import java.util.*

data class SavingsGoal(
    val savingsGoalUid: UUID,
    val name: String,
    val amount: Long)