package com.arnoract.projectx.core.api.model.article.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.core.api.model.article.NetworkArticleSet
import com.arnoract.projectx.domain.model.article.ArticleSet

object NetworkArticleSetToArticleSetMapper : Mapper<NetworkArticleSet?, ArticleSet> {
    override fun map(from: NetworkArticleSet?): ArticleSet {
        return ArticleSet(
            id = from?.id ?: "",
            articleSetIcon = from?.articleSetIcon ?: "",
            articleSetName = from?.articleSetName ?: "",
            articles = from?.articles ?: listOf(),
            backgroundColor = from?.backgroundColor ?: ""
        )
    }
}