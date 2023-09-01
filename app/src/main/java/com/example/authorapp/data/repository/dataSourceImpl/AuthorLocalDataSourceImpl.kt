package com.example.authorapp.data.repository.dataSourceImpl

import com.example.authorapp.data.db.AuthorDao
import com.example.authorapp.data.model.Author
import com.example.authorapp.data.repository.dataSource.AuthorLocalDataSource
import kotlinx.coroutines.flow.Flow

class AuthorLocalDataSourceImpl(private val authorDao: AuthorDao): AuthorLocalDataSource {

    override suspend fun saveAuthorsToDB(authors: List<Author>) {
        authorDao.saveAuthors(authors)

    }
    override fun getSavedAuthors(): Flow<List<Author>> {
        return authorDao.getAllAuthors()
    }

    override suspend fun deleteALl() {
        authorDao.deleteAll()
    }
}