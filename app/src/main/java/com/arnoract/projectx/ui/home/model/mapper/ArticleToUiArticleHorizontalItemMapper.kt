package com.arnoract.projectx.ui.home.model.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.domain.model.article.ArticleCategory
import com.arnoract.projectx.ui.home.model.UiArticleCategory
import com.arnoract.projectx.ui.home.model.UiArticleHorizontalItem

object ArticleToUiArticleHorizontalItemMapper : Mapper<Article, UiArticleHorizontalItem> {
    override fun map(from: Article): UiArticleHorizontalItem {
        return UiArticleHorizontalItem(
            id = from.id ?: "",
            imageUrl = from.imageUrl,
            titleTh = from.titleTh,
            titleEn = from.titleEn,
            category = articleCategoryMapper(from.category),
            descriptionTh = from.descriptionTh,
            descriptionEn = from.descriptionEn,
            viewCount = from.viewCount
        )
    }

    private fun articleCategoryMapper(category: ArticleCategory?): UiArticleCategory? {
        return when (category) {
            ArticleCategory.WORK_LIFE_BALANCE -> UiArticleCategory.WORK_LIFE_BALANCE
            ArticleCategory.SOCIAL_ISSUES -> UiArticleCategory.SOCIAL_ISSUES
            ArticleCategory.SELF_IMPROVEMENT -> UiArticleCategory.SELF_IMPROVEMENT
            ArticleCategory.SUPERSTITIONS_AND_BELIEFS -> UiArticleCategory.SUPERSTITIONS_AND_BELIEFS
            ArticleCategory.POSITIVE_THINKING -> UiArticleCategory.POSITIVE_THINKING
            ArticleCategory.RELATIONSHIPS -> UiArticleCategory.RELATIONSHIPS
            ArticleCategory.VIDEO_GAMES -> UiArticleCategory.VIDEO_GAMES
            ArticleCategory.PRODUCTIVITY -> UiArticleCategory.PRODUCTIVITY
            ArticleCategory.COMMUNICATION_SKILLS -> UiArticleCategory.COMMUNICATION_SKILLS
            ArticleCategory.SOCIETY -> UiArticleCategory.SOCIETY
            else -> null
        }
    }
}