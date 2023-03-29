package com.arnoract.projectx.domain

import com.arnoract.projectx.domain.main.StationRepository
import com.arnoract.projectx.domain.main.StationRepositoryImpl
import com.arnoract.projectx.domain.repository.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<StationRepository> { StationRepositoryImpl(androidContext()) }
    single<ArticleRepository> { ArticleRepositoryImpl(Firebase.firestore, get(), get()) }
    single<UserRepository> { UserRepositoryImpl(Firebase.auth, Firebase.firestore, get(), get()) }
    single<LessonRepository> { LessonRepositoryImpl(Firebase.firestore, get()) }
}