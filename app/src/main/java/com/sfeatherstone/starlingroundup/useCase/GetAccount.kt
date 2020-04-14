package com.sfeatherstone.starlingroundup.useCase

import arrow.core.Either
import com.sfeatherstone.starlingroundup.model.Account
import com.sfeatherstone.starlingroundup.repository.AccountRepository
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
