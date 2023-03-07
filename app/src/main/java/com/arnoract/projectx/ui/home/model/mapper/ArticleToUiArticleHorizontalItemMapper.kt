package com.arnoract.projectx.ui.home.model.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.ui.home.model.UiArticleHorizontalItem
import com.arnoract.projectx.ui.home.model.mapper.ArticleCategoryToUiArticleCategoryMapper

object ArticleToUiArticleHorizontalItemMapper : Mapper<Article, UiArticleHorizontalItem> {
    override fun map(from: Article): UiArticleHorizontalItem {
        return UiArticleHorizontalItem(
            id = from.id ?: "",
            imageUrl = from.imageUrl,
            titleTh = from.titleTh,
            titleEn = from.titleEn,
            category = ArticleCategoryToUiArticleCategoryMapper.map(from.category),
            descriptionTh = from.descriptionTh,
            descriptionEn = from.descriptionEn,
            viewCount = from.viewCount
        )
    }
}