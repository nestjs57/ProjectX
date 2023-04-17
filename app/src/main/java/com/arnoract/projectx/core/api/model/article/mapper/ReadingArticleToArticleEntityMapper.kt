package com.arnoract.projectx.core.api.model.article.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.core.db.entity.ArticleEntity
import com.arnoract.projectx.domain.model.article.ReadingArticle

object ReadingArticleToArticleEntityMapper : Mapper<ReadingArticle, ArticleEntity> {
    override fun map(from: ReadingArticle): ArticleEntity {
        return ArticleEntity(
            _id = from.id,
            titleTh = from.titleTh,
            titleEn = from.titleEn,
            descriptionTh = from.descriptionTh,
            descriptionEn = from.descriptionEn,
            currentParagraph = from.currentParagraph,
            imageUrl = from.imageUrl,
            category = ArticleCategoryToIntArticleMapper.map(from.category),
            totalParagraph = from.totalParagraph
        )
    }
}