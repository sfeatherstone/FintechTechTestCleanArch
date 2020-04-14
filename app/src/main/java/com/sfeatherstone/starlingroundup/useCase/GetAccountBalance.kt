package com.sfeatherstone.starlingroundup.useCase

import arrow.core.Either
import com.sfeatherstone.starlingroundup.model.Account
import com.sfeatherstone.starlingroundup.model.AccountBalance
import com.sfeatherstone.starlingroundup.repository.AccountRepository
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
