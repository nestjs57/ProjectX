package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.domain.repository.ArticleRepository
import com.arnoract.projectx.domain.repository.UserRepository

class GetArticleByIdUseCase(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository
) : UseCase<GetArticleByIdUseCase.Params, Article>() {
    override suspend fun execute(parameters: Params): Article {
        val article = articleRepository.getArticleById(parameters.id)
        val isLogin = userRepository.getIsLogin()
        if (isLogin && parameters.isSubscription) {
            articleRepository.createReadingArticleToDb(article)
        }
        return article
    }

    data class Params(
        val id: String,
        val isSubscription: Boolean
    )
}