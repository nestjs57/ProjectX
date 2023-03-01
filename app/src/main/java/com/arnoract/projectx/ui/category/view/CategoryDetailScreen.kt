package com.arnoract.projectx.ui.category.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
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

@Composable
fun CategoryDetailScreen(categoryId: String, navController: NavHostController) {
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
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
        }
    }

}