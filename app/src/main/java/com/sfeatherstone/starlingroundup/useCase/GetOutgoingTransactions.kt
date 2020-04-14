package com.sfeatherstone.starlingroundup.useCase

import arrow.core.Either
import com.sfeatherstone.starlingroundup.FIXED_SAVINGS_NAME
import com.sfeatherstone.starlingroundup.WEEK
import com.sfeatherstone.starlingroundup.model.Account
import com.sfeatherstone.starlingroundup.model.AccountIdentifier
import com.sfeatherstone.starlingroundup.model.SavingsGoal
import com.sfeatherstone.starlingroundup.model.TransactionSummary
import com.sfeatherstone.starlingroundup.repository.SavingsGoalsRepository
import com.sfeatherstone.starlingroundup.repository.TransactionFeedRepository
import org.threeten.bp.Instant
import timber.log.Timber


class GetOutgoingTransactions(
    private val transactionFeedRepository: TransactionFeedRepository
) : UseCase<TransactionSummary, GetOutgoingTransactions.Params>() {

    data class Params(val account: Account, val savingsGoal: SavingsGoal)

    override suspend fun run(params: Params): Either<Exception, TransactionSummary> {
        return try {
            val weekAgo = Instant.now().minusMillis(WEEK)
            val list = transactionFeedRepository.getAccountFeed(params.account, weekAgo)
            var count = 0
            var amount = 0L
            val filteredList = list.filter {
                val result = it.direction == "OUT" && it.counterPartyUid != params.savingsGoal.savingsGoalUid
                if (result) {
                    count++
                    val remander = it.amount.rem(100L)
                    if (remander > 0) amount += 100L-remander
                }
                result
            }

            Either.right(TransactionSummary(count, amount))
        } catch (ex: Exception) {
            Timber.d(ex, "Exception: $ex")
            Either.Left(ex)
        }
    }
}
