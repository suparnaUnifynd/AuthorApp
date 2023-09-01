package com.example.authorapp.domain.repository

import NetworkResponse
import com.example.authorapp.data.model.Author
import kotlinx.coroutines.flow.Flow


interface AuthorRepository {
    suspend fun getAuthors(pageNo:Int): NetworkResponse<List<Author>>
    suspend fun saveAuthors(user: List<Author>)
    fun getSavedAuthors(): Flow<List<Author>>
    suspend fun deleteAll()
}