package com.arnoract.projectx.domain

import org.koin.core.context.loadKoinModules

object DomainComponent {
    fun init() = loadKoinModules(listOf(useCaseModule, repositoryModule))
}