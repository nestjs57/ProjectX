package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.domain.repository.ArticleRepository

class GetArticlesUseCase(
    private val articleRepository: ArticleRepository
): UseCase<Unit , List<Article>>() {
    override suspend fun execute(parameters: Unit): List<Article> {
       return articleRepository.getArticles()
    }
}