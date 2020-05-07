package com.sfeatherstone.roundup.repository

import com.sfeatherstone.roundup.model.Account
import com.sfeatherstone.roundup.model.AccountFeedItem
import com.sfeatherstone.roundup.repository.network.TransactionsFeedApi
import com.sfeatherstone.roundup.repository.network.mappers.toAccountFeedItemList
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