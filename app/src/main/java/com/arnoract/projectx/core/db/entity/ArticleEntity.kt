package com.arnoract.projectx.core.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val _id: String,
    @ColumnInfo(name = "titleTh")
    val titleTh: String,
    @ColumnInfo(name = "titleEn")
    val titleEn: String,
    @ColumnInfo(name = "descriptionTh")
    val descriptionTh: String,
    @ColumnInfo(name = "descriptionEn")
    val descriptionEn: String,
    @ColumnInfo(name = "currentParagraph")
    val currentParagraph: Int = 0,
    @ColumnInfo(name = "totalParagraph")
    val totalParagraph: Int,
    @ColumnInfo(name = "category")
    val category: Int?,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
)