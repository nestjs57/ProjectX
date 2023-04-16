package com.arnoract.projectx.ui.reader.model

sealed class UiReaderState {
    object Loading : UiReaderState()
    data class Success(
        val titleTh: String,
        val titleEn: String,
        val uiParagraph: List<List<UiParagraph>>,
        val uiTranSlateParagraph: List<String>,
        val currentParagraphSelected: Int = 0,
        val contentRawStateHTML: List<String>,
        val isSubscription: Boolean
    ) : UiReaderState()
}
