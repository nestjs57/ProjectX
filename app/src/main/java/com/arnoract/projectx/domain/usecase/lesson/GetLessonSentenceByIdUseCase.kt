package com.arnoract.projectx.domain.usecase.lesson

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.lesson.LessonSentence
import com.arnoract.projectx.domain.repository.LessonRepository

class GetLessonSentenceByIdUseCase(
    private val lessonRepository: LessonRepository
) : UseCase<String, LessonSentence>() {
    override suspend fun execute(parameters: String): LessonSentence {
        return lessonRepository.getLessonSentence(parameters)
    }
}