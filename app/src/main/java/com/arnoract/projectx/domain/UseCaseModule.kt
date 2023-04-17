package com.arnoract.projectx.domain

import com.arnoract.projectx.domain.main.GetStationsUseCase
import com.arnoract.projectx.domain.usecase.article.*
import com.arnoract.projectx.domain.usecase.lesson.AccessContentWithCoinUseCase
import com.arnoract.projectx.domain.usecase.lesson.GetLessonSentenceByIdUseCase
import com.arnoract.projectx.domain.usecase.lesson.GetLessonSentencesUseCase
import com.arnoract.projectx.domain.usecase.profile.GetProfileUseCase
import com.arnoract.projectx.domain.usecase.profile.LoginWithGoogleUseCase
import com.arnoract.projectx.domain.usecase.profile.SignOutWithGoogleUseCase
import com.arnoract.projectx.domain.usecase.profile.UpdateGoldCoinUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetStationsUseCase(get()) }
    factory { GetArticlesUseCase(get()) }
    factory { GetArticleByIdUseCase(get(), get()) }
    factory { GetProfileUseCase(get()) }
    factory { GetIsLoginUseCase(get()) }
    factory { LoginWithGoogleUseCase(get()) }
    factory { SignOutWithGoogleUseCase(get()) }
    factory { GetArticleByCategoryIdUseCase(get()) }
    factory { GetCurrentParagraphFromDbUseCase(get()) }
    factory { SetCurrentParagraphToDbUseCase(get()) }
    factory { ObserveReadingArticleUseCase(get()) }
    factory { GetReadingArticleUseCase(get()) }
    factory { SetFontSizeSettingUseCase(get()) }
    factory { SyncDataReadingUseCase(get(), get()) }
    factory { SetBackgroundModelSettingUseCase(get()) }
    factory { GetFontSizeSettingUseCase(get()) }
    factory { GetBackgroundModelSettingUseCase(get()) }
    factory { GetLessonSentencesUseCase(get()) }
    factory { UpdateGoldCoinUseCase(get()) }
    factory { AccessContentWithCoinUseCase(get()) }
    factory { GetLessonSentenceByIdUseCase(get()) }
}