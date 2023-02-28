package com.arnoract.projectx.ui.reader.model.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.domain.model.article.Paragraph
import com.arnoract.projectx.ui.reader.model.UiParagraph
import kotlin.random.Random

object ParagraphToUiParagraphMapper : Mapper<Paragraph?, UiParagraph> {
    override fun map(from: Paragraph?): UiParagraph {
        return UiParagraph(
            id = Random.nextInt(1, 999999),
            paragraphNum = from?.paragraphNum ?: 0,
            vocabulary = from?.vocabulary ?: "",
            translate = from?.translate ?: "",
            note = from?.note ?: "",
            example = from?.example ?: "",
            example_translate = from?.example_translate ?: ""
        )
    }
}