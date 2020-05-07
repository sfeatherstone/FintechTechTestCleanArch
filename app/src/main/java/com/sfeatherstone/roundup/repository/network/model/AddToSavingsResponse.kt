package com.sfeatherstone.roundup.repository.network.model

import kotlinx.serialization.*

@Serializable
data class AddToSavingsResponse(val success: Boolean, val transferUid: String)