package com.arnoract.projectx.domain

import com.arnoract.projectx.domain.main.GetStationsUseCase
import com.arnoract.projectx.domain.usecase.article.GetArticleByCategoryIdUseCase
import com.arnoract.projectx.domain.usecase.article.GetArticleByIdUseCase
import com.arnoract.projectx.domain.usecase.article.GetArticleSetByIdUseCase
import com.arnoract.projectx.domain.usecase.article.GetArticleSetUseCase
import com.arnoract.projectx.domain.usecase.article.GetArticlesUseCase
import com.arnoract.projectx.domain.usecase.article.GetBackgroundModelSettingUseCase
import com.arnoract.projectx.domain.usecase.article.GetCurrentParagraphFromDbUseCase
import com.arnoract.projectx.domain.usecase.article.GetFontSizeSettingUseCase
import com.arnoract.projectx.domain.usecase.article.GetIsLoginUseCase
import com.arnoract.projectx.domain.usecase.article.GetReadingArticleUseCase
import com.arnoract.projectx.domain.usecase.article.ObserveReadingArticleUseCase
import com.arnoract.projectx.domain.usecase.article.OnlySyncDataUseCase
import com.arnoract.projectx.domain.usecase.article.RemoveReadingArticleByIdUseCase
import com.arnoract.projectx.domain.usecase.article.SetBackgroundModelSettingUseCase
import com.arnoract.projectx.domain.usecase.article.SetCurrentParagraphToDbUseCase
import com.arnoract.projectx.domain.usecase.article.SetFontSizeSettingUseCase
import com.arnoract.projectx.domain.usecase.article.SyncDataReadingUseCase
import com.arnoract.projectx.domain.usecase.lesson.AccessContentWithCoinUseCase
import com.arnoract.projectx.domain.usecase.lesson.GetLessonSentenceByIdUseCase
import com.arnoract.projectx.domain.usecase.lesson.GetLessonSentencesUseCase
import com.arnoract.projectx.domain.usecase.overview.GetOverviewUseCase
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
    factory { GetArticleSetUseCase(get()) }
    factory { GetArticleSetByIdUseCase(get()) }
    factory { GetCurrentParagraphFromDbUseCase(get()) }
    factory { SetCurrentParagraphToDbUseCase(get()) }
    factory { ObserveReadingArticleUseCase(get()) }
    factory { GetReadingArticleUseCase(get()) }
    factory { SetFontSizeSettingUseCase(get()) }
    factory { SyncDataReadingUseCase(get(), get()) }
    factory { OnlySyncDataUseCase(get(), get()) }
    factory { RemoveReadingArticleByIdUseCase(get(), get()) }
    factory { SetBackgroundModelSettingUseCase(get()) }
    factory { GetFontSizeSettingUseCase(get()) }
    factory { GetBackgroundModelSettingUseCase(get()) }
    factory { GetLessonSentencesUseCase(get()) }
    factory { UpdateGoldCoinUseCase(get()) }
    factory { AccessContentWithCoinUseCase(get()) }
    factory { GetLessonSentenceByIdUseCase(get()) }
    factory { GetOverviewUseCase(get()) }
}