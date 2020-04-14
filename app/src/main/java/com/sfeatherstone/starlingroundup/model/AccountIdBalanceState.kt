package com.sfeatherstone.starlingroundup.model


sealed class AccountIdBalanceState {
    object Loading : AccountIdBalanceState()
    class Success(val data: AccountIdBalance) : AccountIdBalanceState()
    data class Error(val ex: Exception) : AccountIdBalanceState()
}