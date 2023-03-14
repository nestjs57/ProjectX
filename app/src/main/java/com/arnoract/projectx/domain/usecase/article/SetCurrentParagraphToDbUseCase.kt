package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.repository.ArticleRepository

class SetCurrentParagraphToDbUseCase(
    private val articleRepository: ArticleRepository
) : UseCase<SetCurrentParagraphToDbUseCase.Params, Unit>() {

    override suspend fun execute(parameters: Params) {
        articleRepository.setCurrentParagraphToDb(parameters.id, parameters.paragraph)
    }

    data class Params(
        val id: String,
        val paragraph: Int
    )
}