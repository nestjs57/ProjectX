package com.arnoract.projectx.ui.reading.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.arnoract.projectx.ui.reading.viewmodel.ReadingViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ReadingScreen(navController: NavHostController) {
    val viewModel = getViewModel<ReadingViewModel>()

    val data by viewModel.uiData.observeAsState()
}