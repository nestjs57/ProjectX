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
            vocabulary = from?.vocabulary?.trim() ?: "",
            translate = from?.translate?.trim() ?: "",
            note = from?.note?.trim() ?: "",
            example = from?.example?.trim() ?: "",
            example_translate = from?.example_translate?.trim() ?: ""
        )
    }
}