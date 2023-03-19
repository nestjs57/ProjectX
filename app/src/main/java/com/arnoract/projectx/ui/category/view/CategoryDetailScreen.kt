package com.arnoract.projectx.ui.category.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.base.OnEvent
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.category.mapper.CategoryIdToStringCategoryMapper
import com.arnoract.projectx.ui.category.model.UiCategoryDetailState
import com.arnoract.projectx.ui.category.viewmodel.CategoryDetailViewModel
import com.arnoract.projectx.ui.home.view.ArticleHorizontalItem
import com.arnoract.projectx.ui.util.CustomDialog
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CategoryDetailScreen(categoryId: String, navController: NavHostController) {

    val viewModel = getViewModel<CategoryDetailViewModel>(
        parameters = { parametersOf(categoryId) }
    )
    val uiState by viewModel.uiCategoryDetailState.observeAsState()

    SubscribeEvent(viewModel, navController)

    Column {
        Header(navController, categoryId)
        when (val state: UiCategoryDetailState? = uiState) {
            is UiCategoryDetailState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
                }
            }
            is UiCategoryDetailState.Success -> {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    state.data.forEach {
                        item {
                            ArticleHorizontalItem(
                                it
                            ) {
                                viewModel.onNavigateToReader(it)
                            }
                        }
                    }
                }
            }
            is UiCategoryDetailState.Empty -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("items not found.")
                }
            }
            else -> {

            }
        }
    }
}

@Composable
private fun Header(navController: NavHostController, categoryId: String) {
    Row(
        modifier = Modifier
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Menu Btn")
        }
        Text(
            text = stringResource(id = CategoryIdToStringCategoryMapper.map(categoryId)),
            fontSize = 18.sp,
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun SubscribeEvent(viewModel: CategoryDetailViewModel, navController: NavHostController) {
    val openDialog = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    OnEvent(event = viewModel.error, onEvent = {
        openDialog.value = true
        errorMessage.value = it
    })

    OnEvent(event = viewModel.navigateToReader, onEvent = {
        navController.navigate(
            Route.readers.replace(
                "{id}",
                it
            )
        )
    })

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            CustomDialog(openDialogCustom = openDialog, description = errorMessage.value)
        }
    }
}