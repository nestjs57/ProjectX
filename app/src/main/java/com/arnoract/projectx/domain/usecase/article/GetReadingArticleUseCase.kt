package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.article.ReadingArticle
import com.arnoract.projectx.domain.repository.ArticleRepository

class GetReadingArticleUseCase(private val articleRepository: ArticleRepository) :
    UseCase<Unit, List<ReadingArticle>>() {
    override suspend fun execute(parameters: Unit): List<ReadingArticle> {
        return articleRepository.getReadingArticles()
    }
}