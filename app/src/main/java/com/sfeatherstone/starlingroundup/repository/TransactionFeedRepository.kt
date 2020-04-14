package com.sfeatherstone.starlingroundup.repository

import com.sfeatherstone.starlingroundup.model.Account
import com.sfeatherstone.starlingroundup.model.AccountFeedItem
import com.sfeatherstone.starlingroundup.repository.network.TransactionsFeedApi
import com.sfeatherstone.starlingroundup.repository.network.mappers.toAccountFeedItemList
import org.threeten.bp.Instant
import org.threeten.bp.format.DateTimeFormatter.ISO_INSTANT


class TransactionFeedRepository(
    private val transactionsFeedApi: TransactionsFeedApi
) {

    suspend fun getAccountFeed(account: Account, since: Instant): List<AccountFeedItem> {
        val result = transactionsFeedApi.getAccountFeed(
            account.accountUid.toString(),
            account.defaultCategory.toString(),
            ISO_INSTANT.format(since)
            )
        return result.toAccountFeedItemList()
    }

}