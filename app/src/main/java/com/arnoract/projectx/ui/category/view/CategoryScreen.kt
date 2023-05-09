package com.arnoract.projectx.ui.category.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.home.model.UiArticleCategory
import com.arnoract.projectx.ui.home.view.CategorySection

@Composable
fun CategoryScreen(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 24.dp, start = 16.dp, end = 16.dp)
            .background(colorResource(id = R.color.white))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_layout_fluid),
                        modifier = Modifier.size(21.dp),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.black))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "เลือกหมวดหมู่ที่สนใจ",
                        modifier = Modifier,
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                CategorySection(
                    categories = listOf(
                        UiArticleCategory.WORK_LIFE_BALANCE,
                        UiArticleCategory.SOCIAL_ISSUES,
                        UiArticleCategory.SELF_IMPROVEMENT,
                        UiArticleCategory.SUPERSTITIONS_AND_BELIEFS,
                        UiArticleCategory.POSITIVE_THINKING,
                        UiArticleCategory.RELATIONSHIPS,
                        UiArticleCategory.VIDEO_GAMES,
                        UiArticleCategory.PRODUCTIVITY,
                        UiArticleCategory.COMMUNICATION_SKILLS,
                        UiArticleCategory.SOCIETY,
                    )
                ) {
                    Route.category_detail
                    navHostController.navigate(
                        Route.category_detail.replace(
                            "{categoryId}",
                            it.toString()
                        )
                    )
                }
            }
        }
    }
}