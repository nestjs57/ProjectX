package com.arnoract.projectx.domain.repository

import com.arnoract.projectx.domain.model.profile.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserRepositoryImpl : UserRepository {
    override suspend fun getIsLogin(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun getUser(): User {
        val user = Firebase.auth.currentUser
        return User(
            userId = user?.uid ?: "",
            profileUrl = user?.photoUrl.toString(),
            email = user?.email ?: "",
            displayName = user?.displayName ?: ""
        )
    }
}