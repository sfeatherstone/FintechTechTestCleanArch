package com.sfeatherstone.roundup.useCase

import arrow.core.Either
import com.sfeatherstone.roundup.model.Account
import com.sfeatherstone.roundup.repository.AccountRepository
import timber.log.Timber


class GetAccount(
    private val accountRepository: AccountRepository
) : UseCase<Account, Unit>() {

    override suspend fun run(params: Unit): Either<Exception, Account> {
        return try {
            accountRepository.getFirstAccount()?.let {
                Either.right(it)
            } ?: Either.left(Exception("No Account Found"))
        } catch (ex: Exception) {
            Timber.d(ex, "Exception: $ex")
            Either.Left(ex)
        }
    }
}
