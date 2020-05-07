package com.sfeatherstone.roundup.useCase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sfeatherstone.roundup.model.Account
import com.sfeatherstone.roundup.repository.AccountRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class GetAccountTest{
    val uuid1 = UUID.randomUUID()
    val uuid2 = UUID.randomUUID()

    @Test
    fun `when repo throws exception then return left exception`() {

        val accountRepository: AccountRepository = mock()

        val getAccount =
            GetAccount(accountRepository)

        runBlocking {
            whenever(accountRepository.getFirstAccount()).thenThrow(RuntimeException("Something went wrong"))

            val value = getAccount.run(Unit)

            value.fold(
                {
                    assertTrue(it::class == RuntimeException::class)
                    assertEquals("Something went wrong", it.message)
                },
                {
                    assertFalse("Return right value", true)
                }
            )
        }
    }


    @Test
    fun `when repo returns null then return left exception`() {

        val accountRepository: AccountRepository = mock()

        val getAccount =
            GetAccount(accountRepository)

        runBlocking {
            whenever(accountRepository.getFirstAccount()).doReturn(null)

            val value = getAccount.run(Unit)

            value.fold(
                {
                    assertTrue(it::class == Exception::class)
                    assertEquals("No Account Found", it.message)
                },
                {
                    assertFalse("Return right value", true)
                }
            )
        }
    }

    @Test
    fun `when repo returns valid data then return right with correct data`() {
        val accountRepository: AccountRepository = mock()

        val getAccount =
            GetAccount(accountRepository)

        runBlocking {
            whenever(accountRepository.getFirstAccount()).doReturn(
                Account(
                    uuid1,
                    uuid2
                )
            )

            val value = getAccount.run(Unit)

            value.fold(
                {
                    assertFalse("Return left value", true)
                },
                {
                    assertEquals(
                        Account(
                            uuid1,
                            uuid2
                        ), it)
                }
            )
        }
    }

}