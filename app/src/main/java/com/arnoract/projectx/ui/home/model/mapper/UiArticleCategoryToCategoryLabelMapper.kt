package com.arnoract.projectx.ui.home.model.mapper

import com.arnoract.projectx.R
import com.arnoract.projectx.core.Mapper
import com.arnoract.projectx.ui.home.model.UiArticleCategory

object UiArticleCategoryToCategoryLabelMapper : Mapper<UiArticleCategory?, Int> {
    override fun map(from: UiArticleCategory?): Int {
        return when (from) {
            UiArticleCategory.WORK_LIFE_BALANCE -> R.string.work_life_balance_label
            UiArticleCategory.POSITIVE_THINKING -> R.string.positive_thinking_label
            UiArticleCategory.SOCIAL_ISSUES -> R.string.social_issues_label
            UiArticleCategory.SELF_IMPROVEMENT -> R.string.self_improve_label
            UiArticleCategory.SUPERSTITIONS_AND_BELIEFS -> R.string.superstitions_and_beliefs_label
            UiArticleCategory.RELATIONSHIPS -> R.string.relationship_label
            UiArticleCategory.VIDEO_GAMES -> R.string.video_games_label
            UiArticleCategory.PRODUCTIVITY -> R.string.productivity_label
            UiArticleCategory.COMMUNICATION_SKILLS -> R.string.communication_skills_label
            UiArticleCategory.SOCIETY -> R.string.society_label
            else -> R.string.no_category_label
        }
    }
}