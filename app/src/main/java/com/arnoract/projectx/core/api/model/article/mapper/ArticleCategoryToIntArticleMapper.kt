package com.arnoract.projectx.core.api.model.article.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.domain.model.article.ArticleCategory

object ArticleCategoryToIntArticleMapper : Mapper<ArticleCategory?, Int> {
    override fun map(from: ArticleCategory?): Int {
        return when (from) {
            ArticleCategory.WORK_LIFE_BALANCE -> 1
            ArticleCategory.SOCIAL_ISSUES -> 2
            ArticleCategory.SELF_IMPROVEMENT -> 3
            ArticleCategory.SUPERSTITIONS_AND_BELIEFS -> 4
            ArticleCategory.POSITIVE_THINKING -> 5
            ArticleCategory.RELATIONSHIPS -> 6
            ArticleCategory.VIDEO_GAMES -> 7
            ArticleCategory.PRODUCTIVITY -> 8
            ArticleCategory.COMMUNICATION_SKILLS -> 9
            ArticleCategory.SOCIETY -> 10
            null -> 0
        }
    }
}