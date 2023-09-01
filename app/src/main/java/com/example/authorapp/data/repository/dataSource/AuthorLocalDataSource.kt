package com.example.authorapp.data.repository.dataSource

import com.example.authorapp.data.model.Author
import kotlinx.coroutines.flow.Flow

interface AuthorLocalDataSource {
    suspend fun saveAuthorsToDB(authors: List<Author>)
    fun getSavedAuthors(): Flow<List<Author>>
    suspend fun deleteALl()
}