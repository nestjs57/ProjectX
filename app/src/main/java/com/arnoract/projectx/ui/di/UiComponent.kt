package com.arnoract.projectx.ui.di

import com.arnoract.projectx.mainModule
import org.koin.core.context.loadKoinModules

object UiComponent {
    fun init() = loadKoinModules(
        listOf(
            mainModule
        )
    )
}