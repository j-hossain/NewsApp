package com.androiddevs.mvvmnewsapp.di

import android.content.Context
import com.androiddevs.mvvmnewsapp.api.NewsApi
import com.androiddevs.mvvmnewsapp.db.ArticleDao
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.util.Constants.Companion.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit) = retrofit.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient,gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @get:Provides
    @Singleton
    val okHttpClient: OkHttpClient
        get() {
            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            return okHttpClientBuilder.build()
        }

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context) = ArticleDatabase.invoke(context)


    @Provides
    @Singleton
    fun  provideAppDAO(articleDatabase: ArticleDatabase) = articleDatabase.getArticleDao()

    @Provides
    @Singleton
    fun provideRepository(ApiInterface: NewsApi,
                          localDatabase: ArticleDao) =
        NewsRepository(localDatabase,ApiInterface)
}