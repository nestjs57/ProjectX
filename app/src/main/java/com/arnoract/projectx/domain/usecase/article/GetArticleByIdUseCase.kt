package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.domain.repository.ArticleRepository

class GetArticleByIdUseCase(
    private val articleRepository: ArticleRepository
) : UseCase<String, Article>() {
    override suspend fun execute(parameters: String): Article {
        return articleRepository.getArticleById(parameters)
    }
}