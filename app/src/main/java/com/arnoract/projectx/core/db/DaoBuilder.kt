package com.arnoract.projectx.core.db

import com.arnoract.projectx.core.db.dao.ArticleDao

class DaoBuilder(
    private val roomDatabaseStorage: RoomDatabaseStorage,
) {
    fun getArticleDao(): ArticleDao = roomDatabaseStorage.articleDao()
}