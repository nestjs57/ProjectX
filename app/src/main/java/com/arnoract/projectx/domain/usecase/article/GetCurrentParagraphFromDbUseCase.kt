package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.repository.ArticleRepository

class GetCurrentParagraphFromDbUseCase(
    private val articleRepository: ArticleRepository
) : UseCase<String, Int>() {
    override suspend fun execute(parameters: String): Int {
        return articleRepository.getCurrentParagraphFromDbUseCase(parameters)
    }
}