package com.arnoract.projectx.base

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalConfiguration
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> OnEvent(event: Flow<T>, onEvent: (T) -> Unit) {
    LaunchedEffect(Unit) {
        event.collect(onEvent)
    }
}

fun Context.getActivity(): ComponentActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is ComponentActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

@Composable
fun getHeightItemLibrary(): Int {
    return (getWidthItemLibrary() * 1.50).toInt()
}

@Composable
fun getWidthItemLibrary(): Int {
    val configuration = LocalConfiguration.current
    val itemSize = 3
    val paddingHorizontal = 32
    val paddingBetweenItem = 16 * 2
    val allPadding = paddingBetweenItem + paddingHorizontal
    return (configuration.screenWidthDp - allPadding) / itemSize
}