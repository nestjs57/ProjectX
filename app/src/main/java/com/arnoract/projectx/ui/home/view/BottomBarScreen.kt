package com.arnoract.projectx.ui.home.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
        title = "หมวดหมู่",
        icon = Icons.Default.Search
    )

    object Favorite : BottomBarScreen(
        route = "favorite",
        title = "บทเรียน",
        icon = Icons.Default.FavoriteBorder
    )
    object Settings : BottomBarScreen(
        route = "profile",
        title = "ผู้ใช้",
        icon = Icons.Default.Person
    )
}
