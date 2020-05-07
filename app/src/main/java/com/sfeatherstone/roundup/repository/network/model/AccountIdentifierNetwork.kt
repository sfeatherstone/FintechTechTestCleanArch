package com.sfeatherstone.roundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class AccountIdentifierNetwork(val accountIdentifier: String, val bankIdentifier: String)