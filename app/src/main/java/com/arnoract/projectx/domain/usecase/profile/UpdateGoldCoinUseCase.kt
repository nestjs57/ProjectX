package com.arnoract.projectx.domain.usecase.profile

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.repository.UserRepository

class UpdateGoldCoinUseCase(
    private val userRepository: UserRepository
) : UseCase<Int, Int>() {
    override suspend fun execute(parameters: Int): Int {
        return userRepository.updateGoldCoinAfterGetReward(parameters)
    }
}