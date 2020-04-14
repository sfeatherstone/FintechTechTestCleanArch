package com.sfeatherstone.starlingroundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class AccountNetwork(val accountUid: String, val defaultCategory: String)