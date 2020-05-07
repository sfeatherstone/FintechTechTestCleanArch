package com.sfeatherstone.roundup.model


sealed class TransactionSummaryState {
    object Loading : TransactionSummaryState()
    class Success(val data: TransactionSummary) : TransactionSummaryState()
    data class Error(val ex: Exception) : TransactionSummaryState()
}