package com.arnoract.projectx.core.api.model.lesson.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.core.MyGsonBuilder
import com.arnoract.projectx.core.api.model.lesson.NetworkLessonSentence
import com.arnoract.projectx.domain.model.lesson.LessonSentence
import com.arnoract.projectx.domain.model.lesson.Sentence
import com.google.gson.reflect.TypeToken
import java.util.*

object NetworkLessonSentenceToLessonSentenceMapper :
    Mapper<NetworkLessonSentence?, LessonSentence> {
    override fun map(from: NetworkLessonSentence?): LessonSentence {
        return LessonSentence(
            id = from?.id ?: "",
            titleTh = from?.titleTh ?: "",
            titleEn = from?.titleEn ?: "",
            descriptionTh = from?.descriptionTh ?: "",
            descriptionEn = from?.descriptionEn ?: "",
            isComingSoon = from?.isComingSoon ?: false,
            isPublic = from?.isPublic ?: false,
            imageUrl = from?.imageUrl ?: "",
            publicDate = from?.publicDate ?: Date(),
            sentences = from?.contentRawState?.toArrayClass(),
            priceCoin = from?.priceCoin ?: 0
        )
    }

    private fun <T : Any?> String.toArrayClass(): ArrayList<T> {
        val gson = MyGsonBuilder().build()
        val type = object : TypeToken<ArrayList<Sentence>>() {}.type
        return gson.fromJson(this, type)
    }
}