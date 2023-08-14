package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.article.ArticleSet
import com.arnoract.projectx.domain.repository.ArticleRepository

class GetArticleSetByIdUseCase(
    val articleRepository: ArticleRepository
) : UseCase<String, ArticleSet>() {
    override suspend fun execute(parameters: String): ArticleSet {
        return articleRepository.getArticleSetsById(parameters)
    }
}