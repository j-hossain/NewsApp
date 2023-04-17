package com.androiddevs.mvvmnewsapp.repository

import com.androiddevs.mvvmnewsapp.api.NewsApi
import com.androiddevs.mvvmnewsapp.db.ArticleDao
import com.androiddevs.mvvmnewsapp.models.Article
import javax.inject.Inject

class NewsRepository @Inject constructor (val db: ArticleDao, val api: NewsApi) {

    suspend fun getBreakingNews(countryCode:String, pageNumber:Int) =
        api.getBreakingNews(countryCode,pageNumber)
    suspend fun searchNews(searchQuery:String, pageNumber:Int) =
        api.searchForNews(searchQuery,pageNumber)

    suspend fun upsert(article: Article) = db.upsert(article)

    fun getSavedNews() = db.getAllArticles()
    suspend fun deleteArticle(article:Article) = db.deleteArticle(article)
}