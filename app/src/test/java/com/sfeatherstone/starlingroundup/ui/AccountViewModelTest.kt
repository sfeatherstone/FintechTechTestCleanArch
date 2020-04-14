package com.sfeatherstone.starlingroundup.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.sfeatherstone.starlingroundup.model.Account
import com.sfeatherstone.starlingroundup.model.AccountState
import com.sfeatherstone.starlingroundup.useCase.GetAccount
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.*

class AccountViewModelTest{

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    val uuid1 = UUID.randomUUID()
    val uuid2 = UUID.randomUUID()

    @Test
    fun `when useCase returns valid data then livedata shows correct states`() {

        val getAccount = mock<GetAccount> {
            on { invoke(any(), any(), any()) } doAnswer {
                val callback: (Either<Exception, Account>) -> Unit = it.getArgument(2)
                callback.invoke(Either.right(Account(uuid1, uuid2)))
            }
        }


        //val getAccount: GetAccount = mock()
        val mainViewModel = AccountViewModel(getAccount, mock(), mock(), mock(), mock(), mock())

        runBlocking {
            mainViewModel.getAccountDetails()
            assertEquals(AccountState.Success::class, requireNotNull(mainViewModel.account.value)::class)
            assertEquals(Account(uuid1, uuid2), (mainViewModel.account.value as AccountState.Success).data)
        }

    }
}