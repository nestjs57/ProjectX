package com.arnoract.projectx

import com.arnoract.projectx.ui.category.viewmodel.CategoryDetailViewModel
import com.arnoract.projectx.ui.home.viewmodel.HomeViewModel
import com.arnoract.projectx.ui.profile.ProfileViewModel
import com.arnoract.projectx.ui.reader.viewmodel.ReaderViewModel
import com.arnoract.projectx.ui.reading.viewmodel.ReadingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ReadingViewModel(get(), get()) }
    viewModel { (id: String) ->
        ReaderViewModel(id, get(), get(), get(), get())
    }
    viewModel {
        ProfileViewModel(get(), get(), get(), get())
    }
    viewModel { (categoryId: String) ->
        CategoryDetailViewModel(categoryId, get(), get())
    }
}