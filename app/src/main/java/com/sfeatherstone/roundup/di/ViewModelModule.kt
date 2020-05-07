package com.sfeatherstone.roundup.di

import com.sfeatherstone.roundup.ui.AccountViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        AccountViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
