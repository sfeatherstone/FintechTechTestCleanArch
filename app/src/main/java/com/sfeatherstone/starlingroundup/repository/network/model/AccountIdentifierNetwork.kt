package com.sfeatherstone.starlingroundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class AccountIdentifierNetwork(val accountIdentifier: String, val bankIdentifier: String)