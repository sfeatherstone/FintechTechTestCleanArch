package com.sfeatherstone.starlingroundup.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sfeatherstone.starlingroundup.model.*
import com.sfeatherstone.starlingroundup.useCase.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class AccountViewModel(
    private val getAccount: GetAccount,
    private val getAccountBalance: GetAccountBalance,
    private val getAccountIdentifier: GetAccountIdentifier,
    private val getOrCreateSavingsGoal: GetOrCreateSavingsGoal,
    private val getOutgoingTransactions: GetOutgoingTransactions,
    private val addToSavingsGoal: AddToSavingsGoal
): ViewModel() {

    private val accountMutable: MutableLiveData<AccountState> = MutableLiveData<AccountState>()
    val account: LiveData<AccountState> = accountMutable

    private val accountIdBalanceMutable: MutableLiveData<AccountIdBalanceState> = MutableLiveData<AccountIdBalanceState>()
    val accountIdBalance: LiveData<AccountIdBalanceState> = accountIdBalanceMutable

    private val savingsGoalStateMutable: MutableLiveData<SavingsGoalState> = MutableLiveData<SavingsGoalState>()
    val savingsGoalState: LiveData<SavingsGoalState> = savingsGoalStateMutable

    private val transactionSummaryStateMutable: MutableLiveData<TransactionSummaryState> = MutableLiveData<TransactionSummaryState>()
    val transactionSummaryState: LiveData<TransactionSummaryState> = transactionSummaryStateMutable

    private val topupStateMutable: MutableLiveData<TopupState> = MutableLiveData<TopupState>()
    val topupState: LiveData<TopupState> = topupStateMutable

    val transactionUUID = UUID.randomUUID()

    fun getAccountDetails() {
        accountMutable.value = AccountState.Loading
        getAccount.invoke(viewModelScope, Unit) { result ->
            result.fold(
                ifLeft = {
                    accountMutable.value = AccountState.Error(it)
                },
                ifRight = {
                    accountMutable.value = AccountState.Success(it)
                    // Trigger getting account details and savings information
                    getAccountIdBalance(it)
                    getSavingsAccount(it)
                }
            )
        }
    }


    fun getAccountIdBalance(account: Account) {

        //Launch both tasks in parallel. Wait for both to complete

        val balanceJob = CompletableDeferred<AccountBalance>()
        val identifierJob = CompletableDeferred<AccountIdentifier>()
        accountIdBalanceMutable.value = AccountIdBalanceState.Loading
        getAccountBalance.invoke(viewModelScope, account) { result ->
            result.fold(
                ifLeft = {
                    balanceJob.completeExceptionally(it)
                },
                ifRight = {
                    balanceJob.complete(it)
                }
            )
        }
        getAccountIdentifier.invoke(viewModelScope, account) { result ->
            result.fold(
                ifLeft = {
                    identifierJob.completeExceptionally(it)
                },
                ifRight = {
                    identifierJob.complete(it)
                }
            )
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val accountBalance = balanceJob.await()
                    val accountIdentifier = identifierJob.await()
                    accountIdBalanceMutable.postValue(AccountIdBalanceState.Success(
                        AccountIdBalance(accountBalance, accountIdentifier)))
                } catch (e: java.lang.Exception) {
                    accountIdBalanceMutable.postValue(AccountIdBalanceState.Error(e))
                }
            }
        }
    }

    fun getSavingsAccount(account: Account){
        savingsGoalStateMutable.value = SavingsGoalState.Loading
        getOrCreateSavingsGoal.invoke(viewModelScope, account) { result ->
            result.fold(
                ifLeft = {
                    savingsGoalStateMutable.value = SavingsGoalState.Error(it)
                },
                ifRight = {
                    savingsGoalStateMutable.value = SavingsGoalState.Success(it)
                    // Trigger roundup now we know what the savings goal is
                    getRoundupTotal(account, it)
                }
            )
        }
    }

    fun getRoundupTotal(account: Account, savingsGoal: SavingsGoal) {
        transactionSummaryStateMutable.value = TransactionSummaryState.Loading
        getOutgoingTransactions.invoke(viewModelScope, GetOutgoingTransactions.Params(account, savingsGoal)) { result ->
            result.fold(
                ifLeft = {
                    transactionSummaryStateMutable.value = TransactionSummaryState.Error(it)
                },
                ifRight = {
                    transactionSummaryStateMutable.value = TransactionSummaryState.Success(it)
                }
            )
        }
    }

    fun makeSavings(account: Account, savingsGoal: SavingsGoal, transactionUUID: UUID, amount: Long) {
        topupStateMutable.value = TopupState.Loading
        addToSavingsGoal.invoke(viewModelScope, AddToSavingsGoal.Params(account, savingsGoal, transactionUUID, amount)) { result ->
            result.fold(
                ifLeft = {
                    topupStateMutable.value = TopupState.Error(it)
                },
                ifRight = {
                    topupStateMutable.value = TopupState.Success(it)
                }
            )
        }
    }

}