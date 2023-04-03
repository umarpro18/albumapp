package com.example.album.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.album.data.db.AppDatabase
import com.example.album.data.model.AlbumListInfoMapper
import com.example.album.data.remote.GetRemoteAlbumListDataSource
import com.example.album.domain.AlbumListRemoteUseCase
import com.example.album.domain.AlbumService
import com.example.album.domain.repository.AlbumRepository
import com.example.album.utils.CoroutineDispatcherProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://itunes.apple.com/us/rss/topalbums/"

/**
 * A simple class.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Provides
    fun provideAlbumService(retrofit: Retrofit): AlbumService = retrofit.create(
        AlbumService::class.java
    )

    @Singleton
    @Provides
    fun provideAlbumDao(db: AppDatabase) = db.albumDao()

    @Singleton
    @Provides
    fun provideAlbumRemoteUseCase(repository: AlbumRepository) = AlbumListRemoteUseCase(repository)

    @Singleton
    @Provides
    fun provideAlbumRepository(
        getRemoteAlbumListDataSource: GetRemoteAlbumListDataSource,
        dispatcherProvider: CoroutineDispatcherProvider
    ) = AlbumRepository(getRemoteAlbumListDataSource, dispatcherProvider)

    @Singleton
    @Provides
    fun providesAlbumRemoteDataSource(albumService: AlbumService, albumListInfoMapper: AlbumListInfoMapper): GetRemoteAlbumListDataSource =
        GetRemoteAlbumListDataSource(albumService, albumListInfoMapper)

}