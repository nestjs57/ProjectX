package com.arnoract.projectx.core.api.model.article.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.core.db.entity.ArticleEntity
import com.arnoract.projectx.domain.model.article.ReadingArticle

object ArticleEntityToArticleMapper : Mapper<ArticleEntity, ReadingArticle> {
    override fun map(from: ArticleEntity): ReadingArticle {
        return ReadingArticle(
            id = from._id,
            titleTh = from.titleTh,
            titleEn = from.titleEn,
            descriptionTh = from.descriptionTh,
            descriptionEn = from.descriptionEn,
            currentParagraph = from.currentParagraph,
            imageUrl = from.imageUrl,
            category = IntCategoryToArticleCategoryMapper.map(from.category),
            totalParagraph = from.totalParagraph,
            firstDate = from.firstDate,
            lastDate = from.lastDate,
            totalReadTime = from.totalReadTime
        )
    }
}