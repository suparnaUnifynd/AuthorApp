package com.example.authorapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.authorapp.data.api.ApiClient
import com.example.authorapp.data.api.ApiService
import com.example.authorapp.data.db.AuthorDao
import com.example.authorapp.data.db.AuthorDatabase
import com.example.authorapp.data.repository.dataSource.AuthorLocalDataSource
import com.example.authorapp.data.repository.dataSource.AuthorRemoteDataSource
import com.example.authorapp.data.repository.dataSource.AuthorRepositoryImpl
import com.example.authorapp.data.repository.dataSourceImpl.AuthorLocalDataSourceImpl
import com.example.authorapp.data.repository.dataSourceImpl.AuthorRemoteDataSourceImpl
import com.example.authorapp.domain.repository.AuthorRepository
import com.example.authorapp.domain.use_cases.*
import com.example.authorapp.presentation.viewModel.AuthorViewModelFectory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(

        @ApplicationContext
        context: Context

    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://picsum.photos/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiClient.makeOkHttpClient())
            .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthorsDatabase(app: Application): AuthorDatabase {
        return Room.databaseBuilder(app, AuthorDatabase::class.java, "author_db")
                .fallbackToDestructiveMigration()
                .build()

    }

    @Singleton
    @Provides
    fun provideAuthorDao(authorDatabase: AuthorDatabase): AuthorDao {
        return authorDatabase.getAuthorDao()
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(authorDao: AuthorDao): AuthorLocalDataSource {
        return AuthorLocalDataSourceImpl(authorDao)
    }

    @Singleton
    @Provides
    fun provideAuthorRemoteDataSource(
        apiService: ApiService
    ):AuthorRemoteDataSource{
        return AuthorRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideAuthorRepository(
        authorLocalDataSource: AuthorLocalDataSource,
        authorRemoteDataSource: AuthorRemoteDataSource

    ): AuthorRepository {
        return AuthorRepositoryImpl(
            authorLocalDataSource,
            authorRemoteDataSource
        )
    }

    @Singleton
    @Provides
    fun provideAuthorUseCases(
        authorRepository: AuthorRepository
    ): AuthorUseCasesWrapper {
        return AuthorUseCasesWrapper(
            getAuthorsUseCase= GetAuthorsUseCase(authorRepository),
            getSavedAuthorsUseCase = GetSavedAuthorsUseCase(authorRepository),
            saveAuthorsUseCase = SaveAuthorsUseCase(authorRepository),
            deleteAllUseCase = DeleteAllAuthorsCase(authorRepository)
        )
    }


    @Singleton
    @Provides
    fun provideAuthorViewModelFactory(
        application: Application,
        authorUseCasesWrapper: AuthorUseCasesWrapper
    ): AuthorViewModelFectory {
        return AuthorViewModelFectory(
            application,
            authorUseCasesWrapper
        )
    }

}