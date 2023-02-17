package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.home.model.UiArticleCategory

@Composable
fun CategorySection(modifier: Modifier = Modifier, categories: List<UiArticleCategory>) {
    LazyRow(modifier = modifier.fillMaxSize()) {
        items(categories.size) { index ->

            val colorBackground by remember {
                mutableStateOf(getColorTitle(categories[index]))
            }
            val colorTitle by remember {
                mutableStateOf(getTextColorTitle(categories[index]))
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .height(66.dp)
                    .width(130.dp)
                    .background(
                        colorResource(id = colorBackground)
                    )
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = getTitle(categories[index]),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = colorTitle)
                )
            }
        }
    }
}

@Composable
private fun getTitle(value: UiArticleCategory): String {
    return when (value) {
        UiArticleCategory.WORK_LIFE_BALANCE -> stringResource(id = R.string.work_life_balance_category_label)
        UiArticleCategory.SOCIAL_ISSUES -> stringResource(id = R.string.social_issues_category_label)
        UiArticleCategory.SELF_IMPROVEMENT -> stringResource(id = R.string.self_improves_category_label)
        UiArticleCategory.SUPERSTITIONS_AND_BELIEFS -> stringResource(id = R.string.superstitions_and_beliefs_category_label)
        UiArticleCategory.POSITIVE_THINKING -> stringResource(id = R.string.positive_thinking_category_label)
        UiArticleCategory.RELATIONSHIPS -> stringResource(id = R.string.relationship_category_label)
        UiArticleCategory.VIDEO_GAMES -> stringResource(id = R.string.video_games_category_label)
        UiArticleCategory.PRODUCTIVITY -> stringResource(id = R.string.productivity_category_label)
        UiArticleCategory.COMMUNICATION_SKILLS -> stringResource(id = R.string.communication_skills_category_label)
        UiArticleCategory.SOCIETY -> stringResource(id = R.string.society_category_label)
    }
}

private fun getColorTitle(value: UiArticleCategory): Int {
    return when (value) {
        UiArticleCategory.WORK_LIFE_BALANCE -> R.color.bg_category_work_life_balance
        UiArticleCategory.SOCIAL_ISSUES -> R.color.bg_category_social_issues
        UiArticleCategory.SELF_IMPROVEMENT -> R.color.bg_category_self_improvement
        UiArticleCategory.SUPERSTITIONS_AND_BELIEFS -> R.color.bg_category_beliefs
        UiArticleCategory.POSITIVE_THINKING -> R.color.bg_category_positive_thinking
        UiArticleCategory.RELATIONSHIPS -> R.color.bg_category_relation_ships
        UiArticleCategory.VIDEO_GAMES -> R.color.bg_category_video_games
        UiArticleCategory.PRODUCTIVITY -> R.color.bg_category_productivity
        UiArticleCategory.COMMUNICATION_SKILLS -> R.color.bg_category_communication_skills
        UiArticleCategory.SOCIETY -> R.color.bg_category_society
    }
}

private fun getTextColorTitle(value: UiArticleCategory): Int {
    return when (value) {
        UiArticleCategory.WORK_LIFE_BALANCE -> R.color.bg_category_relation_ships
        UiArticleCategory.SOCIAL_ISSUES -> R.color.bg_category_relation_ships
        UiArticleCategory.SELF_IMPROVEMENT -> R.color.bg_category_relation_ships
        UiArticleCategory.SUPERSTITIONS_AND_BELIEFS -> R.color.white
        UiArticleCategory.POSITIVE_THINKING -> R.color.white
        UiArticleCategory.RELATIONSHIPS -> R.color.white
        UiArticleCategory.VIDEO_GAMES -> R.color.white
        UiArticleCategory.PRODUCTIVITY -> R.color.white
        UiArticleCategory.COMMUNICATION_SKILLS -> R.color.white
        UiArticleCategory.SOCIETY -> R.color.white
    }
}