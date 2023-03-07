package com.arnoract.projectx.ui.home.model.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.domain.model.article.ArticleCategory
import com.arnoract.projectx.ui.home.model.UiArticleCategory

object ArticleCategoryToUiArticleCategoryMapper : Mapper<ArticleCategory?, UiArticleCategory?> {
    override fun map(from: ArticleCategory?): UiArticleCategory? {
        return when (from) {
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