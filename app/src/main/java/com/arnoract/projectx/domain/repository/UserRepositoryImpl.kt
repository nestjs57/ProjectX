package com.arnoract.projectx.domain.repository

import com.arnoract.projectx.core.db.dao.ArticleDao
import com.arnoract.projectx.domain.model.profile.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val articleDao: ArticleDao
) : UserRepository {
    override suspend fun getIsLogin(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun getUser(): User {
        val user = auth.currentUser
        return User(
            userId = user?.uid ?: "",
            profileUrl = user?.photoUrl.toString(),
            email = user?.email ?: "",
            displayName = user?.displayName ?: ""
        )
    }

    override suspend fun loginWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credential).await()
    }

    override suspend fun createGoogleAccount(user: User) {
        db.collection("users").document(user.userId)
            .set(user)
            .await()
    }

    override suspend fun signOut() {
        articleDao.delete()
        auth.signOut()
    }
}

