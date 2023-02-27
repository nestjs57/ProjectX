package com.arnoract.projectx.domain.usecase.profile

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.repository.UserRepository

class LoginWithGoogleUseCase(
    private val userRepository: UserRepository
) : UseCase<String, Unit>() {
    override suspend fun execute(parameters: String) {
        userRepository.loginWithGoogle(parameters)
        val user = userRepository.getUser()
        userRepository.createGoogleAccount(user)
    }
}