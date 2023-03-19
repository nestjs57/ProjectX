package com.arnoract.projectx.core.api.model.article.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.core.db.entity.ArticleEntity
import com.arnoract.projectx.domain.model.article.Article

class ArticleToArticleEntityMapper(private val progress: Int) : Mapper<Article, ArticleEntity> {
    override fun map(from: Article): ArticleEntity {
        return ArticleEntity(
            _id = from.id ?: "",
            titleTh = from.titleTh,
            titleEn = from.titleEn,
            descriptionTh = from.descriptionTh,
            descriptionEn = from.descriptionEn,
            currentParagraph = progress,
            imageUrl = from.imageUrl,
            category = ArticleCategoryToIntArticleMapper.map(from.category),
            totalParagraph = from.paragraphsVocabulary?.size ?: 1
        )
    }
}