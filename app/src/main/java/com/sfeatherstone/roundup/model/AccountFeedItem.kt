package com.sfeatherstone.roundup.model

import java.util.*

data class AccountFeedItem(val amount: Long, val direction: String, val counterPartyUid: UUID?)