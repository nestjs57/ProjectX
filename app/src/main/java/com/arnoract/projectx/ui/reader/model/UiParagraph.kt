package com.arnoract.projectx.ui.reader.model

data class UiParagraph(
    val id: Int,
    val paragraphNum: Int,
    val vocabulary: String,
    val translate: String,
    val note: String,
    val isSelected: Boolean = false,
    val example: String,
    val example_translate: String
)
