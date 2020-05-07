package com.sfeatherstone.roundup.di

import com.sfeatherstone.roundup.repository.AccountRepository
import com.sfeatherstone.roundup.repository.SavingsGoalsRepository
import com.sfeatherstone.roundup.repository.TransactionFeedRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { AccountRepository(get()) }
    factory { TransactionFeedRepository(get()) }
    factory { SavingsGoalsRepository(get()) }
}