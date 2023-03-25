package com.arnoract.projectx.domain.usecase.lesson

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.lesson.LessonSentence
import com.arnoract.projectx.domain.repository.LessonRepository

class GetLessonSentencesUseCase(
    private val lessonRepository: LessonRepository
) : UseCase<Unit, List<LessonSentence>>() {
    override suspend fun execute(parameters: Unit): List<LessonSentence> {
        return lessonRepository.getLessonSentences()
    }
}