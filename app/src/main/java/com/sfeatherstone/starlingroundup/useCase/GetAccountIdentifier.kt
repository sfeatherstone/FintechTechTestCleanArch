package com.sfeatherstone.starlingroundup.useCase

import arrow.core.Either
import com.sfeatherstone.starlingroundup.model.Account
import com.sfeatherstone.starlingroundup.model.AccountIdentifier
import com.sfeatherstone.starlingroundup.repository.AccountRepository
import timber.log.Timber


class GetAccountIdentifier(
    private val accountRepository: AccountRepository
) : UseCase<AccountIdentifier, Account>() {

    override suspend fun run(params: Account): Either<Exception, AccountIdentifier> {
        return try {
            Either.right(accountRepository.getIdentifiers(params))
        } catch (ex: Exception) {
            Timber.d(ex, "Exception: $ex")
            Either.Left(ex)
        }
    }
}
