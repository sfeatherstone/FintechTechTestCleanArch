package com.sfeatherstone.starlingroundup.repository.network.mappers

import com.sfeatherstone.starlingroundup.model.AccountFeedItem
import com.sfeatherstone.starlingroundup.repository.network.model.AccountFeed
import java.util.*


fun AccountFeed.toAccountFeedItemList(): List<AccountFeedItem> {
    return this.feedItems.map { AccountFeedItem(
        amount = it.amount.minorUnits,
        direction = it.direction,
        counterPartyUid = it.counterPartyUid?.let { UUID.fromString(it) }
    ) }
}
