package com.sfeatherstone.roundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class AccountNetwork(val accountUid: String, val defaultCategory: String)