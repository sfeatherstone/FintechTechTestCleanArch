package com.sfeatherstone.roundup.model

import java.util.*

sealed class TopupState {
    object Loading : TopupState()
    class Success(val data: UUID) : TopupState()
    data class Error(val ex: Exception) : TopupState()
}