package com.arnoract.projectx.ui.article_set.model.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.ui.article_set.model.UiArticleSetDetail
import com.arnoract.projectx.ui.home.model.mapper.ArticleCategoryToUiArticleCategoryMapper

class ArticleToUiArticleSetDetailMapper(private val currentReadParagraph: Int) :
    Mapper<Article?, UiArticleSetDetail> {
    override fun map(from: Article?): UiArticleSetDetail {
        return UiArticleSetDetail(
            id = from?.id ?: "",
            articleCover = from?.imageUrl ?: "",
            articleName = from?.titleTh ?: "",
            articleCategory = ArticleCategoryToUiArticleCategoryMapper.map(from?.category),
            articleDescription = from?.descriptionTh ?: "",
            percentProgress = currentReadParagraph.toDouble()
                .div(from?.paragraphTranslate?.size?.toDouble() ?: 0.0)
                .times(100).toInt(),
            currentReadParagraph = currentReadParagraph,
            totalParagraphArticle = from?.paragraphTranslate?.size ?: 0
        )
    }
}