package com.arnoract.projectx.core.api.model.article.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.core.api.model.article.NetworkArticle
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.domain.model.article.ArticleCategory
import com.arnoract.projectx.domain.model.article.Paragraph
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object NetworkArticleToArticleMapper : Mapper<NetworkArticle?, Article> {
    override fun map(from: NetworkArticle?): Article {

        val contentRawState: ArrayList<Paragraph>? = from?.contentRawState?.toArrayClass()
        val paragraphs = mutableListOf<List<Paragraph>>()

        contentRawState?.toList()?.groupBy {
            it.paragraphNum
        }?.toList()?.forEachIndexed { _, pair ->
            paragraphs.add(pair.second)
        }

        return Article(
            id = from?.id,
            category = categoryMapper(from?.category),
            imageUrl = from?.imageUrl ?: "",
            titleTh = from?.titleTh ?: "",
            titleEn = from?.titleEn ?: "",
            isRecommend = from?.isRecommend ?: false,
            isPremium = from?.isPremium ?: false,
            descriptionTh = from?.descriptionTh ?: "",
            descriptionEn = from?.descriptionEn ?: "",
            isComingSoon = from?.isComingSoon ?: false,
            publicDate = from?.publicDate ?: Date(),
            viewCount = from?.viewCount ?: 0,
            paragraphs = paragraphs
        )
    }

    private fun categoryMapper(category: Int?): ArticleCategory? {
        return when (category) {
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

    private fun <T : Any?> String.toArrayClass(): ArrayList<T> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Paragraph>>() {}.type
        return gson.fromJson(this, type)
    }
}