package com.arnoract.projectx.ui.home.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "หน้าหลัก",
        icon = Icons.Default.Home
    )

    object Profile : BottomBarScreen(
        route = "search",
        title = "ค้นหา",
        icon = Icons.Default.Search
    )

    object Settings : BottomBarScreen(
        route = "profile",
        title = "ผู้ใช้",
        icon = Icons.Default.Person
    )
}
