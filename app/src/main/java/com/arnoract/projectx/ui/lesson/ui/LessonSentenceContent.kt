package com.arnoract.projectx.ui.lesson.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.lesson.model.UiLessonSentence
import com.arnoract.projectx.ui.lesson.viewmodel.LessonSentenceViewModel
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LessonSentenceContent(
    comingSoon: List<UiLessonSentence>,
    recentlyPublished: List<UiLessonSentence>,
    navController: NavHostController,
    viewModel: LessonSentenceViewModel
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 56.dp)
    ) {
        LazyColumn {
            if (comingSoon.isNotEmpty()) {
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .background(color = colorResource(id = R.color.white))
                            .height(56.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.lesson_coming_soon_label),
                            modifier = Modifier,
                            maxLines = 2,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                if (comingSoon.isNotEmpty()) {
                    items(comingSoon.size) {
                        LessonSentenceItem(
                            comingSoon[it]
                        ) {

                        }
                    }
                }
            }

            if (recentlyPublished.isNotEmpty()) {
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .background(color = colorResource(id = R.color.white))
                            .height(56.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.recently_published_label),
                            modifier = Modifier,
                            maxLines = 2,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                items(recentlyPublished.size) {
                    LessonSentenceItem(
                        recentlyPublished[it]
                    ) {
                        viewModel.onClickedLessonItem(recentlyPublished[it])
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}