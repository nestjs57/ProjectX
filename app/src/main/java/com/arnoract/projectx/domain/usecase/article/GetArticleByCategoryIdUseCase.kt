package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.domain.repository.ArticleRepository

class GetArticleByCategoryIdUseCase(
    private val articleRepository: ArticleRepository
) : UseCase<String, List<Article>>() {
    override suspend fun execute(parameters: String): List<Article> {
        return articleRepository.getArticleByCategoryId(parameters)
    }
}