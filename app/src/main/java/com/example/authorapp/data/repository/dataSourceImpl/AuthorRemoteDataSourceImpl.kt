package com.example.authorapp.data.repository.dataSourceImpl

import com.example.authorapp.data.api.ApiService
import com.example.authorapp.data.model.Author
import com.example.authorapp.data.repository.dataSource.AuthorRemoteDataSource
import retrofit2.Response

class AuthorRemoteDataSourceImpl(private val apiService: ApiService): AuthorRemoteDataSource {
    override suspend fun getAuthors(pageNo: Int): Response<List<Author>> {
        return apiService.getAuthor(pageNo)
    }
}