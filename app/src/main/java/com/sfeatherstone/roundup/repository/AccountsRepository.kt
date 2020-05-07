package com.sfeatherstone.roundup.repository

import com.sfeatherstone.roundup.model.Account
import com.sfeatherstone.roundup.model.AccountBalance
import com.sfeatherstone.roundup.model.AccountIdentifier
import com.sfeatherstone.roundup.repository.network.AccountApi
import com.sfeatherstone.roundup.repository.network.mappers.toAccount
import com.sfeatherstone.roundup.repository.network.mappers.toAccountBalance
import com.sfeatherstone.roundup.repository.network.mappers.toAccountIdentifier


class AccountRepository(
    private val accountApi: AccountApi
) {

    suspend fun getFirstAccount(): Account? {
        val result = accountApi.getAccounts()
        return result.accounts[0].toAccount()
    }

    suspend fun getBalance(account: Account): AccountBalance {
        val result = accountApi.getAccountBalance(account.accountUid.toString())
        return result.toAccountBalance()
    }

    suspend fun getIdentifiers(account: Account): AccountIdentifier {
        val result = accountApi.getAccountIdendifiers(account.accountUid.toString())
        return result.toAccountIdentifier()
    }

}