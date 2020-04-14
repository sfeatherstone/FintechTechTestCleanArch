package com.sfeatherstone.starlingroundup.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.sfeatherstone.starlingroundup.R
import com.sfeatherstone.starlingroundup.model.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val accountViewModel: AccountViewModel by viewModel()

    private var account: Account? = null
    private var savingsGoal: SavingsGoal? = null
    private var transactionSummary: TransactionSummary? = null

    inline fun <T1: Any, T2: Any, T3: Any, R: Any> safeLet(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3)->R?): R? {
        return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accountViewModel.account.observe(this, Observer{ onAccountDetails(it) })
        accountViewModel.accountIdBalance.observe(this, Observer{ onAccountIdBalance(it) })
        accountViewModel.savingsGoalState.observe(this, Observer{ onSavingsGoal(it) })
        accountViewModel.transactionSummaryState.observe(this, Observer{ onTransactionSummary(it) })
        accountViewModel.topupState.observe(this, Observer{ onTopupState(it) })

        button.setOnClickListener {
            safeLet(account, savingsGoal, transactionSummary) { acc, sav, tran ->
                accountViewModel.makeSavings(acc, sav, accountViewModel.transactionUUID, tran.roundup)
            }
        }

        accountViewModel.getAccountDetails()
    }

    fun longToCurrency(amount: Long): String {
        return "£${amount/100.toLong()}.${"%02d".format(amount.rem(100))}"
    }

    private fun onAccountIdBalance(accountIdBalanceState : AccountIdBalanceState) {
        when(accountIdBalanceState) {
            is AccountIdBalanceState.Loading -> accountNum.text = "Loading"
            is AccountIdBalanceState.Error -> accountNum.text = "Error"
            is AccountIdBalanceState.Success -> {
                accountNum.text = accountIdBalanceState.data.identifier.accountNumber
                sort.text = accountIdBalanceState.data.identifier.sortCode
                balance.text = longToCurrency(accountIdBalanceState.data.balance.amount)
            }
        }
    }

    private fun onSavingsGoal(savingsGoalState : SavingsGoalState) {
        when(savingsGoalState) {
            is SavingsGoalState.Loading -> savingsBalance.text = "Loading"
            is SavingsGoalState.Error -> savingsBalance.text = "Error"
            is SavingsGoalState.Success -> {
                savingsBalance.text = longToCurrency(savingsGoalState.data.amount)
                savingsGoal = savingsGoalState.data
            }
        }
    }

    private fun onTransactionSummary(transactionSummaryState : TransactionSummaryState) {
        when(transactionSummaryState) {
            is TransactionSummaryState.Loading -> roundUpTransfer.text = "Loading"
            is TransactionSummaryState.Error -> roundUpTransfer.text = "Error"
            is TransactionSummaryState.Success -> {
                roundUpTransfer.text = longToCurrency(transactionSummaryState.data.roundup)
                transactionSummary = transactionSummaryState.data
            }
        }
    }

    private fun onAccountDetails(accountState : AccountState) {
        when(accountState) {
            is AccountState.Loading -> accountUUID.text = "Loading"
            is AccountState.Error -> accountUUID.text = "Error"
            is AccountState.Success -> {
                accountUUID.text = accountState.data.accountUid.toString()
                account = accountState.data
            }
        }
    }

    private fun onTopupState(topupState : TopupState) {
        when(topupState) {
            is TopupState.Loading -> transactionUUID.text = "Loading"
            is TopupState.Error -> transactionUUID.text = "Error"
            is TopupState.Success -> {
                transactionUUID.text = topupState.data.toString()
            }
        }
    }

}
