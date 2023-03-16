package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.repository.UserRepository

class GetIsLoginUseCase(
    private val userRepository: UserRepository
) : UseCase<Unit, Boolean>() {
    override suspend fun execute(parameters: Unit): Boolean {
        return userRepository.getIsLogin()
    }
}