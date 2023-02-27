package com.arnoract.projectx.domain

import com.arnoract.projectx.domain.main.StationRepository
import com.arnoract.projectx.domain.main.StationRepositoryImpl
import com.arnoract.projectx.domain.repository.ArticleRepository
import com.arnoract.projectx.domain.repository.ArticleRepositoryImpl
import com.arnoract.projectx.domain.repository.UserRepository
import com.arnoract.projectx.domain.repository.UserRepositoryImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<StationRepository> { StationRepositoryImpl(androidContext()) }
    single<ArticleRepository> { ArticleRepositoryImpl(Firebase.firestore) }
    single<UserRepository> { UserRepositoryImpl(Firebase.auth, Firebase.firestore) }
}