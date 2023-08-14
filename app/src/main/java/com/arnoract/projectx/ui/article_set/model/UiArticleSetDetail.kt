package com.arnoract.projectx.ui.article_set.model

import com.arnoract.projectx.ui.home.model.UiArticleCategory

class UiArticleSetDetail(
    val id: String,
    val articleCover: String,
    val articleName: String,
    val articleCategory: UiArticleCategory?,
    val articleDescription: String,
    val percentProgress: Int,
    val currentReadParagraph: Int,
    val totalParagraphArticle: Int
)