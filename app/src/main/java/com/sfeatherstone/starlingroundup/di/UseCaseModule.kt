package com.sfeatherstone.starlingroundup.di

import com.sfeatherstone.starlingroundup.useCase.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetAccount(get()) }
    factory { GetAccountBalance(get()) }
    factory { GetAccountIdentifier(get()) }
    factory { GetOutgoingTransactions(get()) }
    factory { GetOrCreateSavingsGoal(get()) }
    factory { AddToSavingsGoal(get()) }
}