package com.sfeatherstone.roundup.model


sealed class AccountState {
    object Loading : AccountState()
    class Success(val data: Account) : AccountState()
    data class Error(val ex: Exception) : AccountState()
}