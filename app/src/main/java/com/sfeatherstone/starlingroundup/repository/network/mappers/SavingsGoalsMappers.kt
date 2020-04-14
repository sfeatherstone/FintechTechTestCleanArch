package com.sfeatherstone.starlingroundup.repository.network.mappers

import com.sfeatherstone.starlingroundup.model.SavingsGoal
import com.sfeatherstone.starlingroundup.repository.network.model.SavingsGoalsListNetwork
import java.util.*


fun SavingsGoalsListNetwork.toSavingsGoalsList(): List<SavingsGoal> {
    return this.savingsGoalList.map { SavingsGoal(
        savingsGoalUid = UUID.fromString(it.savingsGoalUid),
        name = it.name,
        amount = it.totalSaved.minorUnits
        ) }
}
