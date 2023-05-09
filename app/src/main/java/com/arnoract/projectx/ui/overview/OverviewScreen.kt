package com.arnoract.projectx.ui.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.home.view.BottomBarScreen
import com.arnoract.projectx.ui.overview.model.UiOverviewState
import org.koin.androidx.compose.getViewModel

@Composable
fun OverviewScreen(navController: NavHostController) {

    val viewModel = getViewModel<OverviewViewModel>()

    val uiState = viewModel.uiOverviewState.observeAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_chart_histogram),
                modifier = Modifier.size(21.dp),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.overview_label),
                modifier = Modifier,
                fontSize = 22.sp,
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.Bold
            )
        }
        when (val state = uiState.value) {
            is UiOverviewState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
                }
            }
            is UiOverviewState.Success -> {
                OverviewContent(state, navController)
            }
            is UiOverviewState.NonLogin -> {
                ErrorState(
                    stringResource(id = R.string.overview_require_login_label),
                    navController
                )
            }
            is UiOverviewState.NoSubscription -> {
                ErrorState(
                    stringResource(id = R.string.overview_page_non_subs_label),
                    navController
                )
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
                }
            }
        }
    }
}

@Composable
private fun ErrorState(text: String, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_non_login_overview),
                modifier = Modifier
                    .size(230.dp),
                contentDescription = null,
            )
            Text(
                text = text,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable {
                        navController.navigate(BottomBarScreen.Profile.route) {
                            launchSingleTop = true
                        }
                    }
                    .background(colorResource(id = R.color.purple_500)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.go_to_profile_page_label),
                    modifier = Modifier,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.white),
                )
            }
        }
    }
}