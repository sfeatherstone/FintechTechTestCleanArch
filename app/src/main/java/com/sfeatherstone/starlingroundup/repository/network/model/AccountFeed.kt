package com.sfeatherstone.starlingroundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class AccountFeed(var feedItems: List<FeedItem>)