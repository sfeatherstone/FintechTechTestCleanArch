package com.sfeatherstone.starlingroundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class AccountBalanceNetwork(val effectiveBalance: Money)