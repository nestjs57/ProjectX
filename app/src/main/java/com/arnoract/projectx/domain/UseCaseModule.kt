package com.arnoract.projectx.domain

import com.arnoract.projectx.domain.main.GetStationsUseCase
import com.arnoract.projectx.domain.usecase.article.*
import com.arnoract.projectx.domain.usecase.profile.GetProfileUseCase
import com.arnoract.projectx.domain.usecase.profile.LoginWithGoogleUseCase
import com.arnoract.projectx.domain.usecase.profile.SignOutWithGoogleUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetStationsUseCase(get()) }
    factory { GetArticlesUseCase(get()) }
    factory { GetArticleByIdUseCase(get()) }
    factory { GetProfileUseCase(get()) }
    factory { LoginWithGoogleUseCase(get()) }
    factory { SignOutWithGoogleUseCase(get()) }
    factory { GetArticleByCategoryIdUseCase(get()) }
    factory { GetCurrentParagraphFromDbUseCase(get()) }
    factory { SetCurrentParagraphToDbUseCase(get()) }
    factory { ObserveReadingArticleUseCase(get()) }
}