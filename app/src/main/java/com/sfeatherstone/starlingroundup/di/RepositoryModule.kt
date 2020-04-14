package com.sfeatherstone.starlingroundup.di

import com.sfeatherstone.starlingroundup.repository.AccountRepository
import com.sfeatherstone.starlingroundup.repository.SavingsGoalsRepository
import com.sfeatherstone.starlingroundup.repository.TransactionFeedRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { AccountRepository(get()) }
    factory { TransactionFeedRepository(get()) }
    factory { SavingsGoalsRepository(get()) }
}