package com.arnoract.projectx.core.api.model.user.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.core.api.model.user.NetworkUser
import com.arnoract.projectx.domain.model.profile.User
import java.util.*

object NetworkUserToUserMapper : Mapper<NetworkUser?, User> {
    override fun map(from: NetworkUser?): User {
        return User(
            userId = from?.userId ?: "",
            profileUrl = from?.profileUrl ?: "",
            email = from?.email ?: "",
            displayName = from?.displayName ?: "",
            coin = from?.coin ?: 0,
            readingRawState = from?.readingRawState ?: "",
            createAt = from?.createAt ?: Date()
        )
    }
}