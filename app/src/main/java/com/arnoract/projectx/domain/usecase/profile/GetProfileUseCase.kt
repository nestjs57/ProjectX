package com.arnoract.projectx.domain.usecase.profile

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.exception.UnAuthorizeException
import com.arnoract.projectx.domain.model.profile.User
import com.arnoract.projectx.domain.repository.UserRepository

class GetProfileUseCase(
    private val userRepository: UserRepository
) : UseCase<Unit, User>() {
    override suspend fun execute(parameters: Unit): User {
        if (!userRepository.getIsLogin()) throw UnAuthorizeException()
        return userRepository.getUser()
    }
}