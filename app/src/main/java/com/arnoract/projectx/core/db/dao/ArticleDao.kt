package com.arnoract.projectx.core.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.arnoract.projectx.core.db.entity.ArticleEntity
import com.arnoract.projectx.core.db.model.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ArticleDao : BaseDao<ArticleEntity>() {
    @Query("SELECT * from article")
    abstract fun observeFindAll(): Flow<List<ArticleEntity>>

    @Query("SELECT * from article")
    abstract suspend fun findAll(): List<ArticleEntity>

    @Query("SELECT * from article WHERE id = :id")
    abstract suspend fun findById(id: String): ArticleEntity?

    @Query("DELETE FROM article")
    abstract suspend fun delete()

    @Query("DELETE FROM article WHERE id = :id")
    abstract suspend fun deleteById(id: String)

    @Query("UPDATE article SET currentParagraph = :progress WHERE id = :id")
    abstract suspend fun updateProgressById(id: String, progress: Int)
}