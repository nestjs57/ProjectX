package com.arnoract.projectx.ui.reading.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.domain.model.article.ReadingArticle
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem
import com.arnoract.projectx.ui.home.model.mapper.ArticleCategoryToUiArticleCategoryMapper

object ReadingArticleToUiArticleVerticalItemMapper : Mapper<ReadingArticle, UiArticleVerticalItem> {
    override fun map(from: ReadingArticle): UiArticleVerticalItem {
        return UiArticleVerticalItem(
            id = from.id,
            imageUrl = from.imageUrl,
            titleTh = from.titleTh,
            titleEn = from.titleEn,
            category = ArticleCategoryToUiArticleCategoryMapper.map(from.category),
            progress = ((from.currentParagraph.toDouble()
                .plus(1)).div(from.totalParagraph.toDouble()).times(100).toInt()).toString()
        )
    }
}