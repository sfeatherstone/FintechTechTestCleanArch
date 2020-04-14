package com.sfeatherstone.starlingroundup.useCase

import arrow.core.Either
import com.sfeatherstone.starlingroundup.FIXED_SAVINGS_NAME
import com.sfeatherstone.starlingroundup.model.Account
import com.sfeatherstone.starlingroundup.model.AccountIdentifier
import com.sfeatherstone.starlingroundup.model.SavingsGoal
import com.sfeatherstone.starlingroundup.repository.SavingsGoalsRepository
import timber.log.Timber


class GetOrCreateSavingsGoal(
    private val savingsGoalsRepository: SavingsGoalsRepository
) : UseCase<SavingsGoal, Account>() {

    override suspend fun run(params: Account): Either<Exception, SavingsGoal> {
        return try {
            val list = savingsGoalsRepository.getSavingsGoals(params)
            list.find { it.name == FIXED_SAVINGS_NAME }?.let {
                Either.right(it)
            } ?: run {
                Either.right(savingsGoalsRepository.putSavingsGoal(params))
            }
        } catch (ex: Exception) {
            Timber.d(ex, "Exception: $ex")
            Either.Left(ex)
        }
    }
}
