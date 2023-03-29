package com.arnoract.projectx.ui.lesson.model

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.domain.model.lesson.LessonSentence
import com.arnoract.projectx.domain.model.lesson.Sentence
import com.arnoract.projectx.util.FormatUtils.formatNumberWithoutDecimal

object LessonSentenceToUiLessonSentenceMapper : Mapper<LessonSentence, UiLessonSentence> {
    override fun map(from: LessonSentence): UiLessonSentence {
        return UiLessonSentence(
            id = from.id,
            titleTh = from.titleTh,
            titleEn = from.titleEn,
            descriptionTh = from.descriptionTh,
            descriptionEn = from.descriptionEn,
            isComingSoon = from.isComingSoon,
            isPublic = from.isPublic,
            imageUrl = from.imageUrl,
            publicDate = from.publicDate,
            sentences = from.sentences?.map {
                sentenceToUiSentence(it)
            },
            priceCoin = formatNumberWithoutDecimal(from.priceCoin.toDouble())
        )
    }

    private fun sentenceToUiSentence(from: Sentence): UiSentence {
        return UiSentence(
            sentence = from.sentence,
            translate = from.translate
        )
    }
}