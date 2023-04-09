package com.arnoract.projectx.ui.home.model.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem

object ArticleToUiArticleVerticalItemMapper : Mapper<Article, UiArticleVerticalItem> {
    override fun map(from: Article): UiArticleVerticalItem {
        return UiArticleVerticalItem(
            id = from.id ?: "",
            imageUrl = from.imageUrl,
            titleTh = from.titleTh,
            titleEn = from.titleEn,
            category = ArticleCategoryToUiArticleCategoryMapper.map(from.category),
            isPremium = from.isPremium
        )
    }
}