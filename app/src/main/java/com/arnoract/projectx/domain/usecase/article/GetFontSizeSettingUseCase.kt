package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.repository.ArticleRepository
import com.arnoract.projectx.ui.reader.model.SettingFontSize

class GetFontSizeSettingUseCase(
    private val articleRepository: ArticleRepository
) : UseCase<Unit, SettingFontSize>() {
    override suspend fun execute(parameters: Unit): SettingFontSize {
        return when (articleRepository.getReaderFontSuzeSetting()) {
            1 -> SettingFontSize.SMALL
            2 -> SettingFontSize.NORMAL
            3 -> SettingFontSize.LARGE
            else -> SettingFontSize.NORMAL
        }
    }
}