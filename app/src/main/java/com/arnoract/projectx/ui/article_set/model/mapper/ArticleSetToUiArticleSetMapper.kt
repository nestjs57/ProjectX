package com.arnoract.projectx.ui.article_set.model.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.domain.model.article.ArticleSet
import com.arnoract.projectx.domain.model.article.ReadingArticle
import com.arnoract.projectx.ui.article_set.model.UiArticleSet

class ArticleSetToUiArticleSetMapper(private val articles: List<ReadingArticle>?) :
    Mapper<ArticleSet, UiArticleSet> {
    override fun map(from: ArticleSet): UiArticleSet {
        val allProgress = articles?.sumOf {
            ((it.currentParagraph.toDouble()
                .plus(1)).div(it.totalParagraph.toDouble()).times(100).toInt())
        }

        return UiArticleSet(
            id = from.id,
            articleSetName = from.articleSetName,
            articleCount = from.articles.size,
            articleSetIcon = from.articleSetIcon,
            articleSetBackground = from.backgroundColor,
            articleSetProgress = allProgress?.div(from.articles.size) ?: 0
        )
    }
}