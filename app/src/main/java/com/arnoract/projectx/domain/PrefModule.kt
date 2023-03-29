package com.arnoract.projectx.domain

import com.arnoract.projectx.core.KoinConst
import com.arnoract.projectx.domain.pref.ReaderPreferenceStorage
import com.arnoract.projectx.domain.pref.SharedPreferencesReaderPreferenceStorage
import com.arnoract.projectx.domain.pref.SharedPreferencesUserPreferenceStorage
import com.arnoract.projectx.domain.pref.UserPreferenceStorage
import org.koin.core.qualifier.named
import org.koin.dsl.module


val prefModule = module {
    single<ReaderPreferenceStorage> { SharedPreferencesReaderPreferenceStorage(get(named(KoinConst.SharedPreference.READER_PREFERENCE))) }
    single<UserPreferenceStorage> { SharedPreferencesUserPreferenceStorage(get(named(KoinConst.SharedPreference.USER_PREFERENCE))) }
}