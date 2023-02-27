package com.arnoract.projectx.domain.repository

import com.arnoract.projectx.domain.model.profile.User

interface UserRepository {
    suspend fun getIsLogin(): Boolean
    suspend fun getUser(): User
    suspend fun loginWithGoogle(token: String)
    suspend fun createGoogleAccount(user: User)
    suspend fun signOut()
}