package com.arnoract.projectx.ui.profile.model.mapper

import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.domain.model.profile.User
import com.arnoract.projectx.ui.profile.model.UiUser
import com.arnoract.projectx.util.FormatUtils.formatNumberWithoutDecimal

object UserToUiUserMapper : Mapper<User?, UiUser> {
    override fun map(from: User?): UiUser {
        return UiUser(
            userId = from?.userId ?: "",
            profileUrl = from?.profileUrl ?: "",
            email = from?.email ?: "",
            displayName = from?.displayName ?: "",
            coin = formatNumberWithoutDecimal(from?.coin?.toDouble() ?: 0.0)
        )
    }
}