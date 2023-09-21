package com.example.cliwatch.data.educationData.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cliwatch.data.educationData.entities.Article

@Dao
interface ArticleDao {
    @Insert
    fun insertArticle(article: Article): Long

    @Query("SELECT * FROM articles WHERE articleID = :id")
    fun getArticle(id: Int): Article

    @Query("SELECT * FROM articles")
    fun getAllArticles(): List<Article>

    @Update
    fun updateArticle(article: Article)

    @Delete
    fun deleteArticle(article: Article)
}
