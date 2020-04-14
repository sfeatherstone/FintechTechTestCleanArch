package com.sfeatherstone.starlingroundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class FeedItem(val amount: Money, val direction: String, val counterPartyUid: String? = null)
