package com.sfeatherstone.starlingroundup.di

import com.sfeatherstone.starlingroundup.ui.AccountViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { AccountViewModel(get(), get(), get(), get(), get(), get()) }
}
