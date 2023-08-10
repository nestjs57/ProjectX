package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.article.ArticleSet
import com.arnoract.projectx.domain.repository.ArticleRepository

class GetArticleSetUseCase(
    val articleRepository: ArticleRepository
) : UseCase<Unit, List<ArticleSet>>() {
    override suspend fun execute(parameters: Unit): List<ArticleSet> {
        return articleRepository.getArticleSets()
    }
}