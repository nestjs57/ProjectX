package com.arnoract.projectx.domain.repository

import com.arnoract.projectx.core.api.model.user.mapper.NetworkUserToUserMapper
import com.arnoract.projectx.core.db.dao.ArticleDao
import com.arnoract.projectx.domain.model.profile.User
import com.arnoract.projectx.domain.pref.UserPreferenceStorage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val articleDao: ArticleDao,
    private val userPreferenceStorage: UserPreferenceStorage
) : UserRepository {

    override suspend fun getIsLogin(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun getUser(): User {
        val user = auth.currentUser
        val result = db.collection("users").whereEqualTo("userId", user?.uid).get().await()
        val userModel = if (result.documents.isEmpty()) {
            User(
                userId = user?.uid ?: "",
                profileUrl = user?.photoUrl.toString(),
                email = user?.email ?: "",
                displayName = user?.displayName ?: "",
                coin = 3
            )
        } else {
            NetworkUserToUserMapper.map(result.documents.first().toObject())
        }
        updateUserPref(userModel)
        return userModel
    }

    override suspend fun loginWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credential).await()
    }

    override suspend fun createGoogleAccount(user: User) {
        db.collection("users").document(user.userId).set(user).await()
    }

    override suspend fun updateGoldCoinAfterGetReward(coin: Int): Int {
        val totalCoin = userPreferenceStorage.coin?.plus(coin)
        userPreferenceStorage.coin = totalCoin
        db.collection("users").document(userPreferenceStorage.userId ?: "")
            .update("coin", totalCoin).await()
        return totalCoin ?: userPreferenceStorage.coin ?: 0
    }

    override suspend fun signOut() {
        articleDao.delete()
        auth.signOut()
        clearUerPref()
    }

    private fun clearUerPref() {
        userPreferenceStorage.userId = null
        userPreferenceStorage.displayName = null
        userPreferenceStorage.email = null
        userPreferenceStorage.profileUrl = null
        userPreferenceStorage.coin = null
    }

    private fun updateUserPref(user: User) {
        userPreferenceStorage.userId = user.userId
        userPreferenceStorage.displayName = user.displayName
        userPreferenceStorage.email = user.email
        userPreferenceStorage.profileUrl = user.profileUrl
        userPreferenceStorage.coin = user.coin
    }
}

