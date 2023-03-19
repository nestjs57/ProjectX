package com.arnoract.projectx.domain

import com.arnoract.projectx.core.KoinConst
import com.arnoract.projectx.domain.pref.ReaderPreferenceStorage
import com.arnoract.projectx.domain.pref.SharedPreferencesReaderPreferenceStorage
import org.koin.core.qualifier.named
import org.koin.dsl.module


val prefModule = module {
    single<ReaderPreferenceStorage> { SharedPreferencesReaderPreferenceStorage(get(named(KoinConst.SharedPreference.READER_PREFERENCE))) }
}