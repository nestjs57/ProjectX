package com.arnoract.projectx.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> OnEvent(event: Flow<T>, onEvent: (T) -> Unit) {
    LaunchedEffect(Unit) {
        event.collect(onEvent)
    }
}