package com.arnoract.projectx

import com.arnoract.projectx.ui.article_set.ArticleSetDetailViewModel
import com.arnoract.projectx.ui.article_set.ArticleSetViewModel
import com.arnoract.projectx.ui.category.viewmodel.CategoryDetailViewModel
import com.arnoract.projectx.ui.home.viewmodel.HomeViewModel
import com.arnoract.projectx.ui.lesson.viewmodel.LessonSentenceDetailViewModel
import com.arnoract.projectx.ui.lesson.viewmodel.LessonSentenceViewModel
import com.arnoract.projectx.ui.overview.OverviewViewModel
import com.arnoract.projectx.ui.profile.ProfileViewModel
import com.arnoract.projectx.ui.reader.viewmodel.ExamVocabularyViewModel
import com.arnoract.projectx.ui.reader.viewmodel.ReaderViewModel
import com.arnoract.projectx.ui.reading.viewmodel.ReadingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { ReadingViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { (id: String) ->
        ReaderViewModel(
            id, get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }
    viewModel {
        ExamVocabularyViewModel()
    }
    viewModel {
        ProfileViewModel(get(), get(), get(), get(), get(), get())
    }
    viewModel { (categoryId: String) ->
        CategoryDetailViewModel(categoryId, get(), get(), get(), get(), get())
    }
    viewModel {
        ArticleSetViewModel(get(), get(), get(), get(), get(), get())
    }
    viewModel { (articleSetId: String) ->
        ArticleSetDetailViewModel(articleSetId, get(), get(), get(), get(), get(), get())
    }
    viewModel {
        LessonSentenceViewModel(get(), get(), get(), get())
    }
    viewModel { (id: String) ->
        LessonSentenceDetailViewModel(id, get(), get())
    }
    viewModel {
        SubscriptionViewModel(get(), get())
    }
    single {
        SubscriptionViewModelDelegateImpl(androidContext())
    }
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        OverviewViewModel(get(), get(), get(), get(), get())
    }
}