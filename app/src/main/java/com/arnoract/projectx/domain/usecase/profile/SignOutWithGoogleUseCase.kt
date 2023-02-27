package com.arnoract.projectx.domain.usecase.profile

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.repository.UserRepository

class SignOutWithGoogleUseCase(
    private val userRepository: UserRepository
) : UseCase<Unit, Unit>() {
    override suspend fun execute(parameters: Unit) {
        userRepository.signOut()
    }
}