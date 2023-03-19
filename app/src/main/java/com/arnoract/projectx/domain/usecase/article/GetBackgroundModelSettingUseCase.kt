package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.repository.ArticleRepository
import com.arnoract.projectx.ui.reader.model.SettingBackground

class GetBackgroundModelSettingUseCase(
    private val articleRepository: ArticleRepository
) : UseCase<Unit, SettingBackground>() {
    override suspend fun execute(parameters: Unit): SettingBackground {
        return when (articleRepository.getReaderBackgroundModeSetting()) {
            1 -> SettingBackground.DAY
            2 -> SettingBackground.NIGHT
            else -> SettingBackground.DAY
        }
    }
}