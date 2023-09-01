package com.example.authorapp.data.repository.dataSource

import com.example.authorapp.data.model.Author
import retrofit2.Response


interface AuthorRemoteDataSource {
    suspend fun getAuthors(pageNo:Int): Response<List<Author>>
}