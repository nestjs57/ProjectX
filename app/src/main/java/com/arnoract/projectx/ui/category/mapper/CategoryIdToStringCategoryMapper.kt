package com.arnoract.projectx.ui.category.mapper

import com.arnoract.projectx.R
import com.arnoract.projectx.core.Mapper

object CategoryIdToStringCategoryMapper : Mapper<String, Int> {
    override fun map(from: String): Int {
        return when (from) {
            "1" -> {
                R.string.work_life_balance_category_one_line_label
            }
            "2" -> {
                R.string.social_issues_category_one_line_label
            }
            "3" -> {
                R.string.self_improves_category_one_line_label
            }
            "4" -> {
                R.string.superstitions_and_beliefs_category_one_line_label
            }
            "5" -> {
                R.string.positive_thinking_category_one_line_label
            }
            "6" -> {
                R.string.relationship_category_one_line_label
            }
            "7" -> {
                R.string.video_games_category_one_line_label
            }
            "8" -> {
                R.string.productivity_category_one_line_label
            }
            "9" -> {
                R.string.communication_skills_category_one_line_label
            }
            "10" -> {
                R.string.society_category_one_line_label
            }
            else -> {
                R.string.total_label
            }
        }
    }
}