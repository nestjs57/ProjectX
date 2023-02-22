package com.arnoract.projectx

import com.arnoract.projectx.ui.home.viewmodel.HomeViewModel
import com.arnoract.projectx.ui.profile.ProfileViewModel
import com.arnoract.projectx.ui.reader.viewmodel.ReaderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { (id: String) ->
        ReaderViewModel(id, get(), get())
    }
    viewModel {
        ProfileViewModel(get(), get())
    }
}