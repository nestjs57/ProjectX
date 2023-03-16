package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.domain.repository.ArticleRepository
import com.arnoract.projectx.domain.repository.UserRepository

class GetArticleByIdUseCase(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository
) : UseCase<String, Article>() {
    override suspend fun execute(parameters: String): Article {
        val article = articleRepository.getArticleById(parameters)
        if (userRepository.getIsLogin()) {
            articleRepository.createReadingArticleToDb(article)
        }
        return article
    }
}