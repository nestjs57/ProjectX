package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.repository.ArticleRepository
import com.arnoract.projectx.ui.reader.model.SettingFontSize

class SetFontSizeSettingUseCase(
    private val articleRepository: ArticleRepository
) : UseCase<SettingFontSize, Unit>() {
    override suspend fun execute(parameters: SettingFontSize) {
        val value = when (parameters) {
            SettingFontSize.SMALL -> 1
            SettingFontSize.NORMAL -> 2
            SettingFontSize.LARGE -> 3
        }
        articleRepository.setReaderFontSizeSetting(value)
    }
}