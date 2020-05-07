package com.sfeatherstone.roundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class FeedItem(val amount: Money, val direction: String, val counterPartyUid: String? = null)
