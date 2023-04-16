package com.arnoract.projectx.ui.category.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import com.arnoract.projectx.ui.home.view.BottomBarScreen
import com.arnoract.projectx.ui.util.CustomDialog
import com.arnoract.projectx.ui.util.RequirePremiumDialog
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun getCategoryDetailViewModel(categoryId: String): CategoryDetailViewModel {
    val viewModel = getViewModel<CategoryDetailViewModel> { parametersOf(categoryId) }
    return remember { viewModel }
}

@Composable
fun CategoryDetailScreen(categoryId: String, navController: NavHostController) {

    val viewModel = getCategoryDetailViewModel(categoryId)

    val uiState by viewModel.uiCategoryDetailState.observeAsState()

    SubscribeEvent(viewModel, navController)

    Column {
        Header(viewModel, navController, categoryId)
        when (val state: UiCategoryDetailState? = uiState) {
            is UiCategoryDetailState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
                }
            }
            is UiCategoryDetailState.Success -> {
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                    item { Spacer(modifier = Modifier.height(16.dp)) }
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_kids_reading_amico),
                            modifier = Modifier
                                .size(250.dp),
                            contentDescription = null,
                        )
                        Text(
                            text = stringResource(id = R.string.empty_category_label),
                            fontSize = 20.sp,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            else -> {

            }
        }
    }
}

@Composable
private fun Header(
    viewModel: CategoryDetailViewModel,
    navController: NavHostController,
    categoryId: String
) {
    val filter by viewModel.uiCategoryFilter.observeAsState()
    var isShowFilterDialog by remember {
        mutableStateOf(false)
    }

    if (isShowFilterDialog) {
        BottomDialogFilterCategory(filter, onClickedDismiss = {
            isShowFilterDialog = false
        }, onClickedFilter = {
            viewModel.setFilter(it)
        })
    }

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
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_filter),
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    isShowFilterDialog = true
                },
            contentDescription = null,
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
fun SubscribeEvent(viewModel: CategoryDetailViewModel, navController: NavHostController) {
    val openDialog = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    val openRequirePremiumDialog = remember { mutableStateOf(false) }

    OnEvent(event = viewModel.showDialogErrorNoPremium, onEvent = {
        openRequirePremiumDialog.value = true
    })

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

    if (openRequirePremiumDialog.value) {
        Dialog(onDismissRequest = { openRequirePremiumDialog.value = false }) {
            RequirePremiumDialog(openDialogCustom = openRequirePremiumDialog) {
                openRequirePremiumDialog.value = false
                navController.popBackStack()
                navController.navigate(BottomBarScreen.Profile.route) {
                    launchSingleTop = true
                }
            }
        }
    }

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            CustomDialog(openDialogCustom = openDialog, description = errorMessage.value)
        }
    }
}