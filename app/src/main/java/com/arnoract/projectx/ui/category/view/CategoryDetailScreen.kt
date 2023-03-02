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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.category.mapper.CategoryIdToStringCategoryMapper
import com.arnoract.projectx.ui.category.model.UiCategoryDetailState
import com.arnoract.projectx.ui.category.viewmodel.CategoryDetailViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CategoryDetailScreen(categoryId: String, navController: NavHostController) {

    val viewModel = getViewModel<CategoryDetailViewModel>(
        parameters = { parametersOf(categoryId) }
    )
    val uiState by viewModel.uiCategoryDetailState.observeAsState()

    Column() {
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
        when (val state: UiCategoryDetailState? = uiState) {
            is UiCategoryDetailState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
                }
            }
            is UiCategoryDetailState.Success -> {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    state.data.forEach {
                        item { Text(text = it.titleTh) }
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