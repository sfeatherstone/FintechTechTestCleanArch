package com.sfeatherstone.roundup.repository

import com.sfeatherstone.roundup.DEFAULT_CURRENCY_NAME
import com.sfeatherstone.roundup.FIXED_SAVINGS_NAME
import com.sfeatherstone.roundup.model.Account
import com.sfeatherstone.roundup.model.SavingsGoal
import com.sfeatherstone.roundup.repository.network.SavingsGoalsApi
import com.sfeatherstone.roundup.repository.network.mappers.toSavingsGoalsList
import com.sfeatherstone.roundup.repository.network.model.AddToSavingsGoalRequest
import com.sfeatherstone.roundup.repository.network.model.Money
import com.sfeatherstone.roundup.repository.network.model.NewGoalBodyRequest
import java.util.*

class SavingsGoalsRepository(
    private val savingsGoalsApi: SavingsGoalsApi
) {

    suspend fun getSavingsGoals(account: Account): List<SavingsGoal> {
        val result = savingsGoalsApi.getSavingsGoals(account.accountUid.toString())
        return result.toSavingsGoalsList()
    }

    suspend fun putSavingsGoal(account: Account): SavingsGoal {
        val result = savingsGoalsApi.putSavingsGoal(account.accountUid.toString(),
            NewGoalBodyRequest(
                FIXED_SAVINGS_NAME,
                DEFAULT_CURRENCY_NAME
            )
        )
        return SavingsGoal(
            UUID.fromString(result.savingsGoalUid),
            FIXED_SAVINGS_NAME,
            0
        )
    }

    suspend fun putAddToSavingsGoal(account: Account, savingsGoal: SavingsGoal, transactionUUID: UUID, amount: Long ): UUID {
        val result = savingsGoalsApi.putAddToSavingsGoal(account.accountUid.toString(),
            savingsGoal.savingsGoalUid.toString(),
            transactionUUID.toString(),
            AddToSavingsGoalRequest(
                Money(amount)
            )
        )
        return UUID.fromString(result.transferUid)
    }


}