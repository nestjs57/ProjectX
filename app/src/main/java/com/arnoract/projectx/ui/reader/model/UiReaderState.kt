package com.arnoract.projectx.ui.reader.model

sealed class UiReaderState {
    object Loading : UiReaderState()
    data class Success(
        val titleTh: String,
        val titleEn: String,
        val uiParagraph: List<List<UiParagraph>>,
        val currentParagraphSelected: Int = 0
    ) : UiReaderState()
}
