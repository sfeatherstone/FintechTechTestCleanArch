package com.sfeatherstone.roundup.useCase

import arrow.core.Either
import com.sfeatherstone.roundup.model.Account
import com.sfeatherstone.roundup.model.AccountBalance
import com.sfeatherstone.roundup.repository.AccountRepository
import timber.log.Timber


class GetAccountBalance(
    private val accountRepository: AccountRepository
) : UseCase<AccountBalance, Account>() {

    override suspend fun run(params: Account): Either<Exception, AccountBalance> {
        return try {
            Either.right(accountRepository.getBalance(params))
        } catch (ex: Exception) {
            Timber.d(ex, "Exception: $ex")
            Either.Left(ex)
        }
    }
}
