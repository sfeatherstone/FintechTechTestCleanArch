package com.sfeatherstone.roundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class AccountList(val accounts: List<AccountNetwork>)
