package com.sfeatherstone.roundup.model

import java.util.*

data class Account(
    val accountUid: UUID,
    val defaultCategory: UUID
)