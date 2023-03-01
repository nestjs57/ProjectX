package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.home.model.UiHomeState
import com.arnoract.projectx.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel = getViewModel<HomeViewModel>()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 56.dp, start = 16.dp, end = 16.dp)
            .background(colorResource(id = R.color.white))
    ) {
        val uiHomeState by viewModel.uiHomeState.observeAsState()
        when (val state: UiHomeState? = uiHomeState) {
            is UiHomeState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
                }
            }
            is UiHomeState.Success -> {
                HomeContent(
                    navHostController,
                    state.comingSoonItem,
                    state.recommendedItem,
                    state.recentlyPublished,
                )
            }
            else -> {

            }
        }
    }
}