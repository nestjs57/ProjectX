package com.arnoract.projectx.domain.usecase.lesson

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.repository.LessonRepository

class AccessContentWithCoinUseCase(
    private val lessonRepository: LessonRepository
) : UseCase<Int, Int>() {
    override suspend fun execute(parameters: Int): Int {
        return lessonRepository.accessContentWithCoin(parameters)
    }
}