package com.arnoract.projectx.core.api.model.article.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.domain.model.article.ArticleCategory

object IntCategoryToArticleCategoryMapper : Mapper<Int?, ArticleCategory?> {
    override fun map(from: Int?): ArticleCategory? {
        return when (from) {
            1 -> ArticleCategory.WORK_LIFE_BALANCE
            2 -> ArticleCategory.SOCIAL_ISSUES
            3 -> ArticleCategory.SELF_IMPROVEMENT
            4 -> ArticleCategory.SUPERSTITIONS_AND_BELIEFS
            5 -> ArticleCategory.POSITIVE_THINKING
            6 -> ArticleCategory.RELATIONSHIPS
            7 -> ArticleCategory.VIDEO_GAMES
            8 -> ArticleCategory.PRODUCTIVITY
            9 -> ArticleCategory.COMMUNICATION_SKILLS
            10 -> ArticleCategory.SOCIETY
            else -> null
        }
    }
}