package com.arnoract.projectx.domain

import com.arnoract.projectx.domain.main.GetStationsUseCase
import com.arnoract.projectx.domain.usecase.article.GetArticlesUseCase
import com.arnoract.projectx.domain.usecase.article.GetArticleByIdUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetStationsUseCase(get()) }
    factory { GetArticlesUseCase(get()) }
    factory { GetArticleByIdUseCase(get()) }
}