package com.arnoract.projectx.ui.reading.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOr
import com.arnoract.projectx.domain.usecase.article.ObserveReadingArticleUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReadingViewModel(
    private val observeReadingArticleUseCase: ObserveReadingArticleUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _uiData = MutableLiveData<MutableList<Pair<String, Int>>>()
    val uiData: LiveData<MutableList<Pair<String, Int>>>
        get() = _uiData

    init {
        viewModelScope.launch {
            observeReadingArticleUseCase.invoke(Unit, coroutinesDispatcherProvider.io)
                .collectLatest {
                    val data = it.successOr(listOf())
                }
        }
    }
}

//(it.currentParagraph.toDouble().plus(1)).div(it.totalParagraph.toDouble()).times(100).toInt()