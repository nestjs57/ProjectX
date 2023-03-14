package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.MediatorUseCase
import com.arnoract.projectx.core.Result
import com.arnoract.projectx.domain.model.article.ReadingArticle
import com.arnoract.projectx.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveReadingArticleUseCase(
    private val articleRepository: ArticleRepository
) : MediatorUseCase<Unit, List<ReadingArticle>>() {
    override fun execute(parameters: Unit): Flow<Result<List<ReadingArticle>>> {
        return articleRepository.observeReadingArticles().map { inventories ->
            Result.Success(inventories)
        }
    }
}