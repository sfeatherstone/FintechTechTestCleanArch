package com.sfeatherstone.starlingroundup.model


sealed class SavingsGoalState {
    object Loading : SavingsGoalState()
    class Success(val data: SavingsGoal) : SavingsGoalState()
    data class Error(val ex: Exception) : SavingsGoalState()
}