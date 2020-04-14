package com.sfeatherstone.starlingroundup.useCase

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sfeatherstone.starlingroundup.FIXED_SAVINGS_NAME
import com.sfeatherstone.starlingroundup.model.Account
import com.sfeatherstone.starlingroundup.model.AccountFeedItem
import com.sfeatherstone.starlingroundup.model.SavingsGoal
import com.sfeatherstone.starlingroundup.model.TransactionSummary
import com.sfeatherstone.starlingroundup.repository.AccountRepository
import com.sfeatherstone.starlingroundup.repository.TransactionFeedRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class GetOutgoingTransactionsTest{
    val uuid1 = UUID.randomUUID()
    val uuid2 = UUID.randomUUID()
    val savingGoalUUID = UUID.randomUUID()
    val params = GetOutgoingTransactions.Params(Account(uuid1, uuid2), SavingsGoal(savingGoalUUID, FIXED_SAVINGS_NAME, 0L))

    @Test
    fun `when repo throws exception then return left exception`() {

        val transactionFeedRepository: TransactionFeedRepository = mock()
        val getOutgoingTransactions = GetOutgoingTransactions(transactionFeedRepository)

        runBlocking {
            whenever(transactionFeedRepository.getAccountFeed(any(),any())).thenThrow(RuntimeException("Something went wrong"))

            val value = getOutgoingTransactions.run(params)

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
    fun `when repo returns valid data then return right with correct data`() {
        val transactionFeedRepository: TransactionFeedRepository = mock()
        val getOutgoingTransactions = GetOutgoingTransactions(transactionFeedRepository)

        fun test(returnBlock: List<AccountFeedItem>, expected: TransactionSummary) {
            runBlocking {
                whenever(transactionFeedRepository.getAccountFeed(any(), any())).doReturn(
                    returnBlock
                )

                val value = getOutgoingTransactions.run(params)

                value.fold(
                    {
                        assertFalse("Return left value", true)
                    },
                    {
                        assertEquals(expected, it)
                    }
                )
            }
        }
        test(
            listOf(
                AccountFeedItem(123, "IN", uuid1),
                AccountFeedItem(123, "IN", null),
                AccountFeedItem(123, "IN", uuid2)),
            TransactionSummary(0, 0L)
        )
        test(
            listOf(
                AccountFeedItem(100, "OUT", uuid1),
                AccountFeedItem(0, "OUT", null),
                AccountFeedItem(236712800, "OUT", uuid2)),
            TransactionSummary(3, 0L)
        )
        test(
            listOf(
                AccountFeedItem(101, "OUT", uuid1),
                AccountFeedItem(0, "OUT", null),
                AccountFeedItem(236712899, "OUT", uuid2)),
            TransactionSummary(3, 100L)
        )
        test(
            listOf(
                AccountFeedItem(101, "OUT", uuid1),
                AccountFeedItem(150, "OUT", savingGoalUUID),
                AccountFeedItem(236712899, "OUT", uuid2)),
            TransactionSummary(2, 100L)
        )
        test(
            emptyList(),
            TransactionSummary(0, 0L)
        )
    }

}