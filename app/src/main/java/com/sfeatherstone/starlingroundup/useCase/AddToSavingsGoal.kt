package com.sfeatherstone.starlingroundup.useCase

import arrow.core.Either
import com.sfeatherstone.starlingroundup.FIXED_SAVINGS_NAME
import com.sfeatherstone.starlingroundup.model.Account
import com.sfeatherstone.starlingroundup.model.AccountIdentifier
import com.sfeatherstone.starlingroundup.model.SavingsGoal
import com.sfeatherstone.starlingroundup.repository.SavingsGoalsRepository
import timber.log.Timber
import java.util.*


class AddToSavingsGoal(
    private val savingsGoalsRepository: SavingsGoalsRepository
) : UseCase<UUID, AddToSavingsGoal.Params>() {

    data class Params(val account: Account, val savingsGoal: SavingsGoal, val transactionUUID: UUID, val amount: Long)

    override suspend fun run(params: Params): Either<Exception, UUID> {
        return try {
            Either.right(savingsGoalsRepository.putAddToSavingsGoal(params.account, params.savingsGoal, params.transactionUUID, params.amount))
        } catch (ex: Exception) {
            Timber.d(ex, "Exception: $ex")
            Either.Left(ex)
        }
    }
}
