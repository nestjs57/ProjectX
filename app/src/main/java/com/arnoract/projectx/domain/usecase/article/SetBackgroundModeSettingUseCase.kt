package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.repository.ArticleRepository
import com.arnoract.projectx.ui.reader.model.SettingBackground

class SetBackgroundModelSettingUseCase(
    private val articleRepository: ArticleRepository
) : UseCase<SettingBackground, Unit>() {
    override suspend fun execute(parameters: SettingBackground) {
        val value = when (parameters) {
            SettingBackground.DAY -> 1
            SettingBackground.NIGHT -> 2
        }
        articleRepository.setReaderBackgroundModeSetting(value)
    }
}