package com.arnoract.projectx.core.api.model.article.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.core.api.model.article.NetworkArticle
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.domain.model.article.Paragraph
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object NetworkArticleToArticleMapper : Mapper<NetworkArticle?, Article> {
    override fun map(from: NetworkArticle?): Article {

        val contentRawState: ArrayList<Paragraph>? = from?.contentRawState?.toArrayClass()
        val translateRawState: ArrayList<String>? = from?.translateRawState?.toArrayStringClass()
        val paragraphs = mutableListOf<List<Paragraph>>()

        contentRawState?.toList()?.groupBy {
            it.paragraphNum
        }?.toList()?.forEachIndexed { _, pair ->
            paragraphs.add(pair.second)
        }

        return Article(
            id = from?.id,
            category = IntCategoryToArticleCategoryMapper.map(from?.category),
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
            paragraphTranslate = translateRawState?.toList() ?: listOf(),
            paragraphsVocabulary = paragraphs,
            isPublic = from?.isPublic ?: false
        )
    }

    private fun <T : Any?> String.toArrayClass(): ArrayList<T> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Paragraph>>() {}.type
        return gson.fromJson(this, type)
    }

    private fun <T : Any?> String.toArrayStringClass(): ArrayList<T> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(this, type)
    }
}