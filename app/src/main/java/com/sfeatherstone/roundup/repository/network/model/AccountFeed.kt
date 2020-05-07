package com.sfeatherstone.roundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class AccountFeed(var feedItems: List<FeedItem>)