package com.sfeatherstone.roundup.repository.network.mappers

import com.sfeatherstone.roundup.model.Account
import com.sfeatherstone.roundup.model.AccountBalance
import com.sfeatherstone.roundup.model.AccountIdentifier
import com.sfeatherstone.roundup.repository.network.model.AccountBalanceNetwork
import com.sfeatherstone.roundup.repository.network.model.AccountIdentifierNetwork
import com.sfeatherstone.roundup.repository.network.model.AccountNetwork
import java.util.*


fun AccountNetwork?.toAccount(): Account? {
    return this?.let {
        Account(
            accountUid = UUID.fromString(it.accountUid),
            defaultCategory = UUID.fromString(it.defaultCategory)
        )
    }
}

fun AccountBalanceNetwork.toAccountBalance(): AccountBalance {
    return AccountBalance(this.effectiveBalance.minorUnits)
}

fun AccountIdentifierNetwork.toAccountIdentifier(): AccountIdentifier {
    return AccountIdentifier(
        accountNumber = this.accountIdentifier,
        sortCode = this.bankIdentifier
    )
}